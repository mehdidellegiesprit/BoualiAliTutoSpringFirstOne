package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.Adresse;
import com.bouali.gestiondestock.model.CommandeFournisseur;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
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
