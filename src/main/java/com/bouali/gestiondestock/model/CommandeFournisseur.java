package com.bouali.gestiondestock.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="Commandefournisseur")
public class CommandeFournisseur extends AbstractEntity{

    @Column(name = "code")
    private String code ;

    @Column(name = "datecommande")
    private Instant dateCommande ;

    @Column(name = "etatcommande")
    private EtatCommande etatCommande ;

    @ManyToOne
    @JoinColumn(name="idFournisseur")
    private Fournisseur fournisseur ;

    @OneToMany(mappedBy = "commandeFournisseur")
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs ;

    @Column(name="identreprise")
    private Integer idEntreprise ;
}
