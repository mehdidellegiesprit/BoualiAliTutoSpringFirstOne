package com.bouali.gestiondestock.services;

import com.bouali.gestiondestock.dto.CommandeClientDto;
import com.bouali.gestiondestock.dto.CommandeFournisseurDto;
import com.bouali.gestiondestock.dto.LigneCommandeClientDto;
import com.bouali.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.bouali.gestiondestock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeFournisseurService {

    CommandeFournisseurDto save(CommandeFournisseurDto dto) ;

    CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);


    CommandeFournisseurDto findById(Integer id) ;

    CommandeFournisseurDto findByCode(String code) ;

    List<CommandeFournisseurDto> findALl() ;

    List<LigneCommandeFournisseurDto> findALlLigneCommandesFournisseurByCommandeFournisseurId_service(Integer idCommande) ;

    void delete(Integer id) ;

    CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur);

    CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle);

    CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande);
}
