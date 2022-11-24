package com.bouali.gestiondestock.dto;


import com.bouali.gestiondestock.model.LigneCommandeClient;
import com.bouali.gestiondestock.model.LigneCommandeFournisseur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class LigneCommandeFournisseurDto {

    private Integer id ;

    // fix me plz dall
    @JsonIgnore
    private ArticleDto article ;

    // fix me plz dall
    @JsonIgnore
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
        return ligneCommandeFournisseur;

    }
}
