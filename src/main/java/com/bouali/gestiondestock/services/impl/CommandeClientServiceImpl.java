package com.bouali.gestiondestock.services.impl;

import com.bouali.gestiondestock.Repository.ArticleRepository;
import com.bouali.gestiondestock.Repository.ClientRepository;
import com.bouali.gestiondestock.Repository.CommandeClientRepository;
import com.bouali.gestiondestock.Repository.LigneCommandeClientRepository;
import com.bouali.gestiondestock.dto.ArticleDto;
import com.bouali.gestiondestock.dto.ClientDto;
import com.bouali.gestiondestock.dto.CommandeClientDto;
import com.bouali.gestiondestock.dto.LigneCommandeClientDto;
import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidEntityException;
import com.bouali.gestiondestock.exception.InvalidOperationException;
import com.bouali.gestiondestock.model.*;
import com.bouali.gestiondestock.services.CommandeClientService;
import com.bouali.gestiondestock.validator.ArticleValidator;
import com.bouali.gestiondestock.validator.CommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeClientServiceImpl implements CommandeClientService {

    private CommandeClientRepository commandeClientRepository ;
    private ClientRepository clientRepository ;
    private ArticleRepository articleRepository ;
    private LigneCommandeClientRepository ligneCommandeClientRepository ;

    @Autowired //injection des dependace par constructeur
    public CommandeClientServiceImpl(CommandeClientRepository commandeClientRepository,
                                     ClientRepository clientRepository,
                                     ArticleRepository articleRepository,
                                     LigneCommandeClientRepository ligneCommandeClientRepository){
        this.commandeClientRepository=commandeClientRepository;
        this.clientRepository=clientRepository;
        this.articleRepository=articleRepository;
        this.ligneCommandeClientRepository=ligneCommandeClientRepository;
    }


    @Override
    public CommandeClientDto save(CommandeClientDto dto) {

        // 1ere niveau  de validation de la CommandeClient
        List<String> errors = CommandeClientValidator.validate(dto);

        if(!errors.isEmpty()){
            log.error("Commande client is not valid{} ",dto) ;
            throw new InvalidEntityException("La commande  n'est pas valide", ErrorCodes.COMMANDE_CLIENT_NOT_VALID,errors);
        }

        // TODO save and update are the same methode 90%
        // TODO THE Diffrence between new has not an id yet and if
        // TODO we want to update we have an id
        // TODO check id commande
        // TODO dto.getId() != null   : update


        if (dto.getId() != null && dto.isCommandeLivree()){
            throw new InvalidOperationException("Impossible de modifier la commande lorsque elle est livree",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }


        Optional<Client> client = clientRepository.findById(dto.getClient().getId()) ;
        if (client.isEmpty()){
            log.warn("Client wwith ID {} was not found in the DB", dto.getClient().getId());
            throw new EntityNotFoundException("Aucun client avec l'ID "+dto.getClient().getId()+"n'a ete trouve dans la BDD"
                    ,ErrorCodes.CLIENT_NOT_FOUND);
        }
        List<String> articlesErrors = new ArrayList<>();
        if (dto.getLigneCommandeClients()!=null){
            dto.getLigneCommandeClients().forEach(ligCmdClt->{
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
        CommandeClient savedCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(dto)) ;
        if (dto.getLigneCommandeClients()!=null){
            dto.getLigneCommandeClients().forEach(ligCmdClt->{
                LigneCommandeClient ligneCommandeClient = LigneCommandeClientDto.toEntity(ligCmdClt) ;
                ligneCommandeClient.setCommandeClient(savedCmdClt);
                ligneCommandeClientRepository.save(ligneCommandeClient);
            });
        }
        return  CommandeClientDto.fromEntity(savedCmdClt);
    }

    @Override
    public CommandeClientDto findById(Integer id) {
        if (id==null){
            log.error("commande client ID is NULL ");
            return null ;
        }
        return commandeClientRepository.findById(id)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(()->new EntityNotFoundException(
                        "Aucune commande client n'a ete trouve avec l'ID "+id,ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));
    }

    @Override
    public CommandeClientDto findByCode(String code) {
        if (!StringUtils.hasLength(code)){
            log.error("commande client CODE  is NULL ");
            return null ;
        }
        return commandeClientRepository.findCommandeClientByCode(code)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(()->new EntityNotFoundException(
                        "Aucune commande client n'a ete trouve avec le CODE  "+code,ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));
    }

    @Override
    public List<CommandeClientDto> findALl() {
        return commandeClientRepository.findAll().stream()
                .map(CommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id==null){
            log.error("commande client ID is NULL ");
            return ;
        }
        commandeClientRepository.deleteById(id);
    }

    @Override
    public CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if (!StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("l'etat de la commande client is NULL ");
            throw new InvalidOperationException("Impossible de modifier l' etat de la commande avec un etat  null ",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        commandeClient.setEtatCommande(etatCommande);

        CommandeClient updatedCmd = commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient));

        return CommandeClientDto.fromEntity(updatedCmd) ;
    }

    @Override
    public CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande) ;

        if (quantite==null || quantite.compareTo(BigDecimal.ZERO)==0){
            log.error("l'ID de la ligne commande client is NULL ");
            throw new InvalidOperationException("Impossible de modifier la commande avec une quantite null ou zero",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }

        CommandeClientDto commandeClient = checkEtatCommande(idCommande) ;

        Optional<LigneCommandeClient> ligneCommandeClientOptional = findLigneCommandeClient(idLigneCommande) ;

        LigneCommandeClient ligneCommandeClient = ligneCommandeClientOptional.get();

        ligneCommandeClient.setQuantite(quantite);

        ligneCommandeClientRepository.save(ligneCommandeClient) ;

        return commandeClient;
    }

    @Override
    public CommandeClientDto updateClient(Integer idCommande, Integer idClient) {
        checkIdCommande(idCommande);
        if (idClient==null){
            log.error("l'ID de client is NULL ");
            throw new InvalidOperationException("Impossible de modifier la commande avec un ID client null ",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        Optional<Client> clientOptional = clientRepository.findById(idClient);
        if (clientOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucune  client n'a ete trouve avec l'ID  "+idClient,ErrorCodes.CLIENT_NOT_FOUND);
        }

        commandeClient.setClient(ClientDto.fromEntity(clientOptional.get()));

        return CommandeClientDto.fromEntity(
                commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient))
        );
    }



    @Override
    public CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande,Integer idArticle) {
        // TODO modifier l 'id d'un article d'une commande (ligne commande)

        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        checkIdArticle(idArticle,"nouvel");

        CommandeClientDto commandeClient = checkEtatCommande(idCommande) ;

        Optional<LigneCommandeClient> ligneCommandeClient = findLigneCommandeClient(idLigneCommande) ;

        Optional<Article> articleOptional = articleRepository.findById(idArticle) ;

        if (articleOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucune article n'a ete trouve avec l'ID  "+idArticle,ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));

        if (!errors.isEmpty()){
            throw new InvalidEntityException("Article Invalid",ErrorCodes.ARTICLE_NOT_VALID,errors);
        }

        LigneCommandeClient ligneCommandeClientToSaved = ligneCommandeClient.get();

        ligneCommandeClientToSaved.setArticle(articleOptional.get());

        ligneCommandeClientRepository.save(ligneCommandeClientToSaved) ;

        return commandeClient;
    }

    @Override
    public CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        //Just to check th LigneCommandeClient and inform the client if it is absent
        findLigneCommandeClient(idLigneCommande) ;

        ligneCommandeClientRepository.deleteById(idLigneCommande);
        return commandeClient ;
    }

    @Override
    public List<LigneCommandeClientDto> findALlLigneCommandesClientByCommandeClientId(Integer idCommande) {
        return ligneCommandeClientRepository.findAllLigneCommandeClientByCommandeClientId(idCommande).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    private void checkIdCommande(Integer idCommande){
        if (idCommande==null){
            log.error("commande client ID is NULL ");
            throw new InvalidOperationException("Impossible de modifier l' etat de la commande avec un ID null ",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande){
        if (idLigneCommande==null){
            log.error("l'ID de la ligne commande client is NULL ");
            throw new InvalidOperationException("Impossible de modifier la commande avec une ligne de commande  null ",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }
    }

    private void checkIdArticle(Integer idArticle,String msg){
        if (idArticle==null){
            log.error("l'ID de "+msg+" article is NULL ");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un "+msg+" ID  Article null",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }
    }
    private CommandeClientDto checkEtatCommande(Integer idCommande) {
        CommandeClientDto commandeClient = findById(idCommande);
        if (commandeClient.isCommandeLivree()){
            throw new InvalidOperationException("Impossible de modifier la commande lorsque elle est livree",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }
        return commandeClient ;
    }

    private Optional<LigneCommandeClient> findLigneCommandeClient(Integer idLigneCommande) {
        Optional<LigneCommandeClient> ligneCommandeClientOptional = ligneCommandeClientRepository.findById(idLigneCommande);
        if (ligneCommandeClientOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucune Ligne commande  client n'a ete trouve avec l'ID  "+idLigneCommande,ErrorCodes.LIGNE_COMMANDE_CLIENT_NOT_FOUND);
        }
        return ligneCommandeClientOptional;
    }
}
