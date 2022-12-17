package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.LigneCommandeFournisseur;
import com.bouali.gestiondestock.model.LigneVente;
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
public class LigneVenteDto {

    private Integer id ;

    private VentesDto vente ;

    private BigDecimal quantite ;

    // le prix d'achat important dans l historique
    private BigDecimal prixUnitaire ;

    private ArticleDto article ;


    public static LigneVenteDto fromEntity (LigneVente ligneVente){

        if (ligneVente==null){
            return null ;
        }
        return LigneVenteDto.builder()
                .id(ligneVente.getId())
                .quantite(ligneVente.getQuantite())
                .prixUnitaire(ligneVente.getPrixUnitaire())
                .vente(VentesDto.fromEntity(ligneVente.getVente()))
                .article(ArticleDto.fromEntity(ligneVente.getArticle()))
                .build();
    }

    public static LigneVente toEntity (LigneVenteDto ligneVenteDto){
        if (ligneVenteDto==null){
            return null ;
        }
        LigneVente ligneVente = new LigneVente() ;
        ligneVente.setId(ligneVenteDto.getId());
        ligneVente.setQuantite(ligneVenteDto.getQuantite());
        ligneVente.setPrixUnitaire(ligneVenteDto.getPrixUnitaire());
        ligneVente.setVente(VentesDto.toEntity(ligneVenteDto.getVente()));
        ligneVente.setArticle(ArticleDto.toEntity(ligneVenteDto.getArticle()));
        return ligneVente;

    }
}
