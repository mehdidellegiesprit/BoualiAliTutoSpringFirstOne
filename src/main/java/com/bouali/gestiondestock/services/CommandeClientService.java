package com.bouali.gestiondestock.services;

import com.bouali.gestiondestock.dto.ArticleDto;
import com.bouali.gestiondestock.dto.CommandeClientDto;
import com.bouali.gestiondestock.dto.LigneCommandeClientDto;
import com.bouali.gestiondestock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeClientService {

    CommandeClientDto save(CommandeClientDto dto) ;

    CommandeClientDto updateEtatCommande(Integer idCommande , EtatCommande etatCommande) ;

    CommandeClientDto updateQuantiteCommande(Integer idCommande , Integer idLigneCommande, BigDecimal quantite) ;

    CommandeClientDto updateClient (Integer idCommande , Integer idClient) ;

    //Update Article ==> update l'id de l'article qui existe dans la ligne de commande client
    CommandeClientDto updateArticle (Integer idCommande , Integer idLigneCommande, Integer idArticle);

    // Delete Article ==> delete LigneCommandeClient
    CommandeClientDto deleteArticle(Integer idCommande , Integer idLigneCommande) ;

    CommandeClientDto findById(Integer id) ;

    CommandeClientDto findByCode(String code) ;

    List<CommandeClientDto> findALl() ;

    List<LigneCommandeClientDto> findALlLigneCommandesClientByCommandeClientId(Integer idCommande) ;

    void delete(Integer id) ;
}
