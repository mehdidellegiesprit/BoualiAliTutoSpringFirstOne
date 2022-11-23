package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.Adresse;
import com.bouali.gestiondestock.model.Utilisateur;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
import java.util.List;

@Builder
@Data
public class EntrepriseDto {
    private String nom ;

    private String description ;

    private AdresseDto adresse ;

    private String codeFiscal ;

    private String photo ;

    private String email ;

    private String numTel ;

    private String steWeb ;

    private List<UtilisateurDto> utilisateurs ;
}
