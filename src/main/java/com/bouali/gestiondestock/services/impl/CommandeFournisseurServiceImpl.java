package com.bouali.gestiondestock.services.impl;

import com.bouali.gestiondestock.Repository.*;
import com.bouali.gestiondestock.dto.*;
import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidEntityException;
import com.bouali.gestiondestock.exception.InvalidOperationException;
import com.bouali.gestiondestock.model.*;
import com.bouali.gestiondestock.services.CommandeFournisseurService;
import com.bouali.gestiondestock.services.MvtStkService;
import com.bouali.gestiondestock.validator.ArticleValidator;
import com.bouali.gestiondestock.validator.CommandeClientValidator;
import com.bouali.gestiondestock.validator.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private CommandeFournisseurRepository commandeFournisseurRepository ;
    private FournisseurRepository fournisseurRepository ;
    private ArticleRepository articleRepository ;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository ;
    private MvtStkService mvtStkService ;

    @Autowired //injection des dependace par constructeur
    public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository,
                                          FournisseurRepository fournisseurRepository,
                                          ArticleRepository articleRepository,
                                          LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository,
                                          MvtStkService mvtStkService){
        this.commandeFournisseurRepository=commandeFournisseurRepository;
        this.fournisseurRepository=fournisseurRepository;
        this.articleRepository=articleRepository;
        this.ligneCommandeFournisseurRepository=ligneCommandeFournisseurRepository;
        this.mvtStkService=mvtStkService;
    }


    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {

        // 1ere niveau  de validation de la CommandeFournisseurDto
        List<String> errors = CommandeFournisseurValidator.validate(dto);

        if(!errors.isEmpty()){
            log.error("Commande Fournisseur is not valid{} ",dto) ;
            throw new InvalidEntityException("La commande  n'est pas valide", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID,errors);
        }
        // verifier si le fournisseur exist !
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(dto.getFournisseur().getId()) ;
        if (fournisseur.isEmpty()){
            log.warn("fournisseur with ID {} was not found in the DB", dto.getFournisseur().getId());
            throw new EntityNotFoundException("Aucun Fournisseur avec l'ID "+dto.getFournisseur().getId()+"n'a ete trouve dans la BDD"
                    ,ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }
        // verifier les articles de fournisseur !
        List<String> articlesErrors = new ArrayList<>();
        if (dto.getLigneCommandeFournisseurs()!=null){
            dto.getLigneCommandeFournisseurs().forEach(ligCmdClt->{
                if (ligCmdClt.getArticle() != null) {
                    Optional<Article> article = articleRepository.findById(ligCmdClt.getArticle().getId()) ;
                    if (article.isEmpty()){
                        articlesErrors.add("L'article avec l'id "+ligCmdClt.getArticle().getId()+"n'existe pas ") ;
                    }
                }else{
                    articlesErrors.add("Impossible d'enregistrer une commande avec un attribut NULL ") ;
                }
            });
        }
        if (!articlesErrors.isEmpty()){
            log.warn("") ;
            throw  new InvalidEntityException("Article n'existe pas dans la BDD",ErrorCodes.ARTICLE_NOT_FOUND,articlesErrors);
        }
        //enregistrer la commande fournisseur et la ligne commande Fournisseur dans la BD
        CommandeFournisseur savedCmdFrns = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(dto)) ;
        if (dto.getLigneCommandeFournisseurs()!=null){
            dto.getLigneCommandeFournisseurs().forEach(ligCmdFrs->{
                LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDto.toEntity(ligCmdFrs) ;
                ligneCommandeFournisseur.setCommandeFournisseur(savedCmdFrns);
                ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
            });
        }
        return  CommandeFournisseurDto.fromEntity(savedCmdFrns);
    }

    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande) ;

        if (quantite==null || quantite.compareTo(BigDecimal.ZERO)==0){
            log.error("l'ID de la ligne commande fournisseur is NULL ");
            throw new InvalidOperationException("Impossible de modifier la commande fournisseur avec une quantite null ou zero",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_MODIFIABLE);
        }

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande) ;

        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = findLigneCommandeFournisseur(idLigneCommande) ;

        LigneCommandeFournisseur ligneCommandeFournisseur = ligneCommandeFournisseurOptional.get();

        ligneCommandeFournisseur.setQuantite(quantite);

        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur) ;

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if (id==null){
            log.error("commande Fournisseur ID is NULL ");
            return null ;
        }
        return commandeFournisseurRepository.findById(id)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(()->new EntityNotFoundException(
                        "Aucune commande Fournisseur n'a ete trouve avec l'ID "+id,ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {

        if (!StringUtils.hasLength(code)){
            log.error("commande Fournisseur CODE  is NULL ");
            return null ;
        }
        return commandeFournisseurRepository.findCommandeFournisseurByCode(code)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(()->new EntityNotFoundException(
                        "Aucune commande Fournisseur n'a ete trouve avec le CODE  "+code,ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));

    }

    @Override
    public List<CommandeFournisseurDto> findALl() {
        return commandeFournisseurRepository.findAll().stream()
                .map(CommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findALlLigneCommandesFournisseurByCommandeFournisseurId_service(Integer idCommande) {
        return ligneCommandeFournisseurRepository.findALlLigneCommandesFournisseurByCommandeFournisseurId(idCommande).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id==null){
            log.error("commande Fournisseur ID is NULL ");
            return ;
        }
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findALlLigneCommandesFournisseurByCommandeFournisseurId(id) ;
        if (!ligneCommandeFournisseurs.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer uune commande fournisseur deja utilisee ",
                    ErrorCodes.COMMANDE_FOURNISSEUR_ALREADY_IN_USE) ;
        }
        commandeFournisseurRepository.deleteById(id);
    }

    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if (!StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("l'etat de la commande fournisseur is NULL ");
            throw new InvalidOperationException("Impossible de modifier l' etat de la commande avec un etat  null ",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_MODIFIABLE);
        }
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        commandeFournisseur.setEtatCommande(etatCommande);
        CommandeFournisseur savedCommande = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur));
        if (commandeFournisseur.isCommandeLivree()){
            updateMvtStk(idCommande);
        }
        return CommandeFournisseurDto.fromEntity(savedCommande) ;
    }

    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
        checkIdCommande(idCommande);
        if (idFournisseur==null){
            log.error("l'ID de fournisseur is NULL ");
            throw new InvalidOperationException("Impossible de modifier la commande avec un ID fournisseur null ",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_MODIFIABLE);
        }

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

        Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idFournisseur);
        if (fournisseurOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucune  fournisseur n'a ete trouve avec l'ID  "+idFournisseur,ErrorCodes.CLIENT_NOT_FOUND);
        }

        commandeFournisseur.setFournisseur(FournisseurDto.fromEntity(fournisseurOptional.get()));

        return CommandeFournisseurDto.fromEntity(
                commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur))
        );
    }

    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        checkIdArticle(idArticle,"nouvel");

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande) ;

        Optional<LigneCommandeFournisseur> ligneCommandeFournisseur = findLigneCommandeFournisseur(idLigneCommande) ;

        Optional<Article> articleOptional = articleRepository.findById(idArticle) ;

        if (articleOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucune article n'a ete trouve avec l'ID  "+idArticle,ErrorCodes.ARTICLE_NOT_FOUND);
        }
        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));

        if (!errors.isEmpty()){
            throw new InvalidEntityException("Article Invalid",ErrorCodes.ARTICLE_NOT_VALID,errors);
        }

        LigneCommandeFournisseur ligneCommandeFournisseurToSaved = ligneCommandeFournisseur.get();
        ligneCommandeFournisseurToSaved.setArticle(articleOptional.get());
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSaved) ;

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        //Just to check th LigneCommandeClient and inform the client if it is absent
        findLigneCommandeFournisseur(idLigneCommande) ;

        ligneCommandeFournisseurRepository.deleteById(idLigneCommande);
        return commandeFournisseur ;
    }

    private void checkIdCommande(Integer idCommande){
        if (idCommande==null){
            log.error("commande fournisseur ID is NULL ");
            throw new InvalidOperationException("Impossible de modifier l' etat de la commande avec un ID null ",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }
    }
    private CommandeFournisseurDto checkEtatCommande(Integer idCommande) {
        CommandeFournisseurDto commandeFournisseur = findById(idCommande);
        if (commandeFournisseur.isCommandeLivree()){
            throw new InvalidOperationException("Impossible de modifier la commande lorsque elle est livree",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_MODIFIABLE);
        }
        return commandeFournisseur ;
    }

    private void checkIdLigneCommande(Integer idLigneCommande){
        if (idLigneCommande==null){
            log.error("l'ID de la ligne commande fournisseur is NULL ");
            throw new InvalidOperationException("Impossible de modifier la commande fournisseur avec une ligne de commande  null ",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }
    }

    private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseur(Integer idLigneCommande) {
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = ligneCommandeFournisseurRepository.findById(idLigneCommande);
        if (ligneCommandeFournisseurOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucune Ligne commande  fournisseur n'a ete trouve avec l'ID  "+idLigneCommande,ErrorCodes.LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND);
        }
        return ligneCommandeFournisseurOptional;
    }

    private void checkIdArticle(Integer idArticle,String msg){
        if (idArticle==null){
            log.error("l'ID de "+msg+" article is NULL ");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande fournisseur avec un "+msg+" ID  Article null",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }
    }

    private void updateMvtStk (Integer idCommande){
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findALlLigneCommandesFournisseurByCommandeFournisseurId((idCommande));
        ligneCommandeFournisseurs.forEach(lig->{
            MvtStkDto mvtStkDto = MvtStkDto.builder()
                    .article(ArticleDto.fromEntity(lig.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvt(TypeMvtStk.ENTREE)
                    .sourceMvt(SourceMvtStk.COMMANDE_FOURNISSEUR)
                    .quantite(lig.getQuantite())
                    .idEntreprise(lig.getIdEntreprise())
                    .build();
            mvtStkService.entreeStock(mvtStkDto);
        });
    }
}
