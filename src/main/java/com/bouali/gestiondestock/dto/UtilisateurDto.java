package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.Adresse;
import com.bouali.gestiondestock.model.Entreprise;
import com.bouali.gestiondestock.model.Roles;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Builder
@Data
public class UtilisateurDto {
    private String nom;

    private String prenom;

    private String email;

    private Instant dateDeNaissance;

    private String motDePasse;

    private AdresseDto adresse ;

    private String photo ;

    private EntrepriseDto entreprise ;

    private List<RoleDto> roles ;
}
