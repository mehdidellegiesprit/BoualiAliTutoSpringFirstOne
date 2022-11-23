package com.bouali.gestiondestock.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="lignecommandefournisseur")
public class LigneCommandeFournisseur extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name="idarticle")
    private  Article article ;

    @ManyToOne
    @JoinColumn(name="idcommandefournisseur")
    private CommandeFournisseur commandeFournisseur ;

    @Column(name = "quantite")
    private BigDecimal quantite ;

    // le prix d'achat important dans l historique
    @Column(name = "prixunitaire")
    private BigDecimal prixUnitaire ;

}
