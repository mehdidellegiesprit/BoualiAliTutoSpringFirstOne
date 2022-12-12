package com.bouali.gestiondestock.services;

import com.bouali.gestiondestock.dto.CategoryDto;
import com.bouali.gestiondestock.dto.RolesDto;
import com.bouali.gestiondestock.model.Roles;

import java.util.List;

public interface RolesService {
    RolesDto save(RolesDto dto) ;

    RolesDto findById(Integer id) ;

    List<RolesDto>  findRolesByUtilisateur(Integer idUtilisateur) ;

    List<RolesDto> findALl() ;

    void delete(Integer id) ;
}
