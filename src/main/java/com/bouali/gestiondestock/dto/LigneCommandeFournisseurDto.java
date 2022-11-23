package com.bouali.gestiondestock.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class LigneCommandeFournisseurDto {

    private ArticleDto article ;


    private CommandeFournisseurDto commandeFournisseur ;

    private BigDecimal quantite ;

    // le prix d'achat important dans l historique
    private BigDecimal prixUnitaire ;
}
