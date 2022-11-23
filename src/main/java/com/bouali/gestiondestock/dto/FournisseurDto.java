package com.bouali.gestiondestock.dto;


import lombok.Builder;
import lombok.Data;


import java.util.List;

@Builder
@Data
public class FournisseurDto {
    private String nom ;

    private String prenom ;

    private AdresseDto adresse ;

    private String photo ;

    private String mail ;

    private String numTel ;

    private List<CommandeFournisseurDto> commandeFournisseurs ;
}
