package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.Fournisseur;
import com.bouali.gestiondestock.model.LigneCommandeFournisseur;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.List;

@Builder
@Data
public class CommandeFournisseurDto {
    private String code ;

    private Instant dateCommande ;

    private FournisseurDto fournisseur ;

    private List<LigneCommandeFournisseurDto> ligneCommandeFournisseurs ;
}
