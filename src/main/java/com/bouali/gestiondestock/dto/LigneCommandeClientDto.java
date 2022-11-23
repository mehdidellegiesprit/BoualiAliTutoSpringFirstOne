package com.bouali.gestiondestock.dto;


import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;

@Builder
@Data
public class LigneCommandeClientDto {

    private ArticleDto article ;


    private CommandeClientDto commandeClient ;

    private BigDecimal quantite ;

    // le prix d'achat important dans l historique
    private BigDecimal prixUnitaire ;
}
