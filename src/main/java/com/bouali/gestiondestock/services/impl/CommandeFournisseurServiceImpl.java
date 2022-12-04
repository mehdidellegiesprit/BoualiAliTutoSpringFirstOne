package com.bouali.gestiondestock.services.impl;

import com.bouali.gestiondestock.Repository.*;
import com.bouali.gestiondestock.dto.CommandeClientDto;
import com.bouali.gestiondestock.dto.CommandeFournisseurDto;
import com.bouali.gestiondestock.dto.LigneCommandeClientDto;
import com.bouali.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidEntityException;
import com.bouali.gestiondestock.model.*;
import com.bouali.gestiondestock.services.CommandeFournisseurService;
import com.bouali.gestiondestock.validator.CommandeClientValidator;
import com.bouali.gestiondestock.validator.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired //injection des dependace par constructeur
    public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository,
                                          FournisseurRepository fournisseurRepository,
                                          ArticleRepository articleRepository,
                                          LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository){
        this.commandeFournisseurRepository=commandeFournisseurRepository;
        this.fournisseurRepository=fournisseurRepository;
        this.articleRepository=articleRepository;
        this.ligneCommandeFournisseurRepository=ligneCommandeFournisseurRepository;
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
    public void delete(Integer id) {
        if (id==null){
            log.error("commande Fournisseur ID is NULL ");
            return ;
        }
        commandeFournisseurRepository.deleteById(id);
    }
}
