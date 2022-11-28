package com.bouali.gestiondestock.model;

import lombok.*;

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

    @Column(name="identreprise")
    private Integer idEntreprise ;
}
