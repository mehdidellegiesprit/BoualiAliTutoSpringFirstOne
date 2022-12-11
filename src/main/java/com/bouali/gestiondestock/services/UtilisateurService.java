package com.bouali.gestiondestock.services;

import com.bouali.gestiondestock.dto.FournisseurDto;
import com.bouali.gestiondestock.dto.UtilisateurDto;

import java.util.List;

public interface UtilisateurService {
    UtilisateurDto save(UtilisateurDto dto) ;

    UtilisateurDto findById(Integer id) ;

    UtilisateurDto findByEmail(String email) ;

    List<UtilisateurDto> findALl() ;

    void delete(Integer id) ;
}
