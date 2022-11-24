package com.bouali.gestiondestock.dto;


import com.bouali.gestiondestock.model.Adresse;
import com.bouali.gestiondestock.model.LigneCommandeClient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;

@Builder
@Data
public class LigneCommandeClientDto {

    private Integer id ;

    // fix me plz dall
    @JsonIgnore
    private ArticleDto article ;

    // fix me plz dall
    @JsonIgnore
    private CommandeClientDto commandeClient ;

    private BigDecimal quantite ;

    // le prix d'achat important dans l historique
    private BigDecimal prixUnitaire ;

    public static LigneCommandeClientDto fromEntity (LigneCommandeClient ligneCommandeClient){

        if (ligneCommandeClient==null){
            return null ;
        }
        return LigneCommandeClientDto.builder()
                .id(ligneCommandeClient.getId())
                .quantite(ligneCommandeClient.getQuantite())
                .prixUnitaire(ligneCommandeClient.getPrixUnitaire())
                .build();
    }

    public static LigneCommandeClient toEntity (LigneCommandeClientDto ligneCommandeClientDto){
        if (ligneCommandeClientDto==null){
            return null ;
        }
        LigneCommandeClient ligneCommandeClient = new LigneCommandeClient() ;
        ligneCommandeClient.setId(ligneCommandeClientDto.getId());
        ligneCommandeClient.setQuantite(ligneCommandeClientDto.getQuantite());
        ligneCommandeClient.setPrixUnitaire(ligneCommandeClientDto.getPrixUnitaire());
        return ligneCommandeClient;

    }
}
