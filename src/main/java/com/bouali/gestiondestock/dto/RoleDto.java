package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.Utilisateur;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Builder
@Data
public class RoleDto {

    private String roleName ;

    private UtilisateurDto utilisateur ;
}
