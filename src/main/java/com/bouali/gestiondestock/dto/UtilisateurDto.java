package com.bouali.gestiondestock.dto;


import com.bouali.gestiondestock.model.Utilisateur;
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
public class UtilisateurDto {

    private Integer id ;

    private String nom;

    private String prenom;

    private String email;

    private Instant dateDeNaissance;

    private String motDePasse;

    // fix me plz dall
    //@JsonIgnore
    private AdresseDto adresse ;

    private String photo ;


    // fix me plz dall
    //@JsonIgnore
    private EntrepriseDto entreprise ;

    // fix me plz dall
    //@JsonIgnore
    private List<RolesDto> roles ;
    public static UtilisateurDto fromEntity(Utilisateur utilisateur){
        if (utilisateur==null){
            return null ;
            //TODO throw an exception
        }
        //Mapping de Category  => CategoryDto
        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .dateDeNaissance(utilisateur.getDateDeNaissance())
                .motDePasse(utilisateur.getMotDePasse())
                .photo(utilisateur.getPhoto())
                .adresse(AdresseDto.fromEntity(utilisateur.getAdresse()))
                .entreprise(EntrepriseDto.fromEntity(utilisateur.getEntreprise()))
                .build();
    }




    public static Utilisateur toEntity(UtilisateurDto utilisateurDto){
        if (utilisateurDto==null){
            return null ;
            //TODO throw an exception
        }
        //Mapping de  CategoryDto => Category

        Utilisateur  utilisateur= new Utilisateur() ;
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setDateDeNaissance(utilisateurDto.getDateDeNaissance());
        utilisateur.setMotDePasse(utilisateurDto.getMotDePasse());
        utilisateur.setPhoto(utilisateurDto.getMotDePasse());
        utilisateur.setAdresse(AdresseDto.toEntity(utilisateurDto.getAdresse()));
        utilisateur.setEntreprise(EntrepriseDto.toEntity(utilisateurDto.getEntreprise()));
        return utilisateur;
    }
}
