package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.CommandeClient;
import com.bouali.gestiondestock.model.CommandeFournisseur;
import com.bouali.gestiondestock.model.EtatCommande;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CommandeFournisseurDto {

    private Integer id ;

    private String code ;

    private Instant dateCommande ;

    private EtatCommande etatCommande ;


    private FournisseurDto fournisseur ;

    private Integer idEntreprise ;

    @JsonIgnore
    private List<LigneCommandeFournisseurDto> ligneCommandeFournisseurs ;

    public static CommandeFournisseurDto fromEntity (CommandeFournisseur commandeFournisseur){

        if (commandeFournisseur==null){
            return null ;
        }
        return CommandeFournisseurDto.builder()
                .id(commandeFournisseur.getId())
                .code(commandeFournisseur.getCode())
                .dateCommande(commandeFournisseur.getDateCommande())
                .etatCommande(commandeFournisseur.getEtatCommande())
                .fournisseur(FournisseurDto.fromEntity(commandeFournisseur.getFournisseur()))
                .idEntreprise(commandeFournisseur.getIdEntreprise())
                .build();
    }

    public static CommandeFournisseur toEntity (CommandeFournisseurDto commandeFournisseurDto){
        if (commandeFournisseurDto==null){
            return null ;
        }
        CommandeFournisseur commandeFournisseur = new CommandeFournisseur() ;
        commandeFournisseur.setId(commandeFournisseurDto.getId());
        commandeFournisseur.setCode(commandeFournisseurDto.getCode());
        commandeFournisseur.setDateCommande(commandeFournisseurDto.getDateCommande());
        commandeFournisseur.setEtatCommande(commandeFournisseurDto.getEtatCommande());
        commandeFournisseur.setFournisseur(FournisseurDto.toEntity(commandeFournisseurDto.getFournisseur()));
        commandeFournisseur.setIdEntreprise(commandeFournisseurDto.getIdEntreprise()); //!!!!!!! kamel lo5rin
        return commandeFournisseur;

    }
    public boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommande) ;
    }
}
