package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.Adresse;
import com.bouali.gestiondestock.model.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RoleDto {

    private Integer id ;

    private String roleName ;

    // fix me plz dall
    //@JsonIgnore
    private UtilisateurDto utilisateur ;

    public static RoleDto fromEntity (Roles roles){

        if (roles==null){
            return null ;
        }
        return RoleDto.builder()
                .id(roles.getId())
                .roleName(roles.getRoleName())
                .utilisateur(UtilisateurDto.fromEntity(roles.getUtilisateur()))
                .build();
    }

    public static Roles toEntity (RoleDto roleDto){
        if (roleDto==null){
            return null ;
        }
        Roles roles = new Roles() ;
        roles.setId(roleDto.getId());
        roles.setRoleName(roleDto.getRoleName());
        roles.setUtilisateur(UtilisateurDto.toEntity(roleDto.getUtilisateur()));
        return roles;

    }
}
