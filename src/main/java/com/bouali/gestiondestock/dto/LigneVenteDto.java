package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.LigneCommandeFournisseur;
import com.bouali.gestiondestock.model.LigneVente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class LigneVenteDto {

    private Integer id ;

    // fix me plz dall
    @JsonIgnore
    private VentesDto vente ;

    private BigDecimal quantite ;

    // le prix d'achat important dans l historique
    private BigDecimal prixUnitaire ;
    public static LigneVenteDto fromEntity (LigneVente ligneVente){

        if (ligneVente==null){
            return null ;
        }
        return LigneVenteDto.builder()
                .id(ligneVente.getId())
                .quantite(ligneVente.getQuantite())
                .prixUnitaire(ligneVente.getPrixUnitaire())
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
        return ligneVente;

    }
}
