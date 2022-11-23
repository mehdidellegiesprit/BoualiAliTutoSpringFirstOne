package com.bouali.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class RoleDto {

    private String roleName ;

    private UtilisateurDto utilisateur ;
}
