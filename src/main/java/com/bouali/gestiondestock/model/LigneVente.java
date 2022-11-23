package com.bouali.gestiondestock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="lignevente")
public class LigneVente extends AbstractEntity{


    @ManyToOne
    @JoinColumn(name = "idvente")
    private Ventes vente ;

    @Column(name = "quantite")
    private BigDecimal quantite ;

    // le prix d'achat important dans l historique
    @Column(name = "prixunitaire")
    private BigDecimal prixUnitaire ;
}
