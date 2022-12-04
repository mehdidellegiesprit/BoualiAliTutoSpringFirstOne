package com.bouali.gestiondestock.services;

import com.bouali.gestiondestock.dto.ClientDto;
import com.bouali.gestiondestock.dto.EntrepriseDto;

import java.util.List;

public interface EntrepriseService {
    EntrepriseDto save(EntrepriseDto dto) ;

    EntrepriseDto findById(Integer id) ;

    List<EntrepriseDto> findALl() ;

    void delete(Integer id) ;
}
