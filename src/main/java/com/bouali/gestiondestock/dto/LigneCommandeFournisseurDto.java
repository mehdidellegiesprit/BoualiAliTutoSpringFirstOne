package com.bouali.gestiondestock.dto;


import com.bouali.gestiondestock.model.LigneCommandeClient;
import com.bouali.gestiondestock.model.LigneCommandeFournisseur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LigneCommandeFournisseurDto {

    private Integer id ;

    private ArticleDto article ;

    private CommandeFournisseurDto commandeFournisseur ;

    private BigDecimal quantite ;

    // le prix d'achat important dans l historique
    private BigDecimal prixUnitaire ;
    public static LigneCommandeFournisseurDto fromEntity (LigneCommandeFournisseur ligneCommandeFournisseur){

        if (ligneCommandeFournisseur==null){
            return null ;
        }
        return LigneCommandeFournisseurDto.builder()
                .id(ligneCommandeFournisseur.getId())
                .quantite(ligneCommandeFournisseur.getQuantite())
                .prixUnitaire(ligneCommandeFournisseur.getPrixUnitaire())
                .article(ArticleDto.fromEntity(ligneCommandeFournisseur.getArticle()))
                .commandeFournisseur(CommandeFournisseurDto.fromEntity(ligneCommandeFournisseur.getCommandeFournisseur()))
                .build();
    }

    public static LigneCommandeFournisseur toEntity (LigneCommandeFournisseurDto ligneCommandeFournisseurDto){
        if (ligneCommandeFournisseurDto==null){
            return null ;
        }
        LigneCommandeFournisseur ligneCommandeFournisseur = new LigneCommandeFournisseur() ;
        ligneCommandeFournisseur.setId(ligneCommandeFournisseurDto.getId());
        ligneCommandeFournisseur.setQuantite(ligneCommandeFournisseurDto.getQuantite());
        ligneCommandeFournisseur.setPrixUnitaire(ligneCommandeFournisseurDto.getPrixUnitaire());
        ligneCommandeFournisseur.setArticle(ArticleDto.toEntity(ligneCommandeFournisseurDto.getArticle()));
        ligneCommandeFournisseur.setCommandeFournisseur(CommandeFournisseurDto.toEntity(ligneCommandeFournisseurDto.getCommandeFournisseur()));
        return ligneCommandeFournisseur;

    }
}
