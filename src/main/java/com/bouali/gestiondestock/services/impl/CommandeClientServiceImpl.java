package com.bouali.gestiondestock.services.impl;

import com.bouali.gestiondestock.Repository.ArticleRepository;
import com.bouali.gestiondestock.Repository.ClientRepository;
import com.bouali.gestiondestock.Repository.CommandeClientRepository;
import com.bouali.gestiondestock.Repository.LigneCommandeClientRepository;
import com.bouali.gestiondestock.dto.CommandeClientDto;
import com.bouali.gestiondestock.dto.LigneCommandeClientDto;
import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidEntityException;
import com.bouali.gestiondestock.model.Article;
import com.bouali.gestiondestock.model.Client;
import com.bouali.gestiondestock.model.CommandeClient;
import com.bouali.gestiondestock.model.LigneCommandeClient;
import com.bouali.gestiondestock.services.CommandeClientService;
import com.bouali.gestiondestock.validator.CommandeClientValidator;
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
}
