package com.bouali.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;

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
