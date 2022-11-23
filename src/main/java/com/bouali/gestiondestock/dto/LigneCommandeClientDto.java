package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.Article;
import com.bouali.gestiondestock.model.CommandeClient;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
