package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RolesDto {

    private Integer id ;

    private String roleName ;

    // fix me plz dall
    //@JsonIgnore
    private UtilisateurDto utilisateur ;

    public static RolesDto fromEntity (Roles roles){

        if (roles==null){
            return null ;
        }
        return RolesDto.builder()
                .id(roles.getId())
                .roleName(roles.getRoleName())
                .utilisateur(UtilisateurDto.fromEntity(roles.getUtilisateur()))
                .build();
    }

    public static Roles toEntity (RolesDto rolesDto){
        if (rolesDto ==null){
            return null ;
        }
        Roles roles = new Roles() ;
        roles.setId(rolesDto.getId());
        roles.setRoleName(rolesDto.getRoleName());
        roles.setUtilisateur(UtilisateurDto.toEntity(rolesDto.getUtilisateur()));
        return roles;

    }
}
