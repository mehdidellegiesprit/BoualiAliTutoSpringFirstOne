package com.bouali.gestiondestock.services;

import com.bouali.gestiondestock.dto.CommandeClientDto;
import com.bouali.gestiondestock.dto.VentesDto;

import java.util.List;

public interface VentesService {

    VentesDto save(VentesDto dto) ;

    VentesDto findById(Integer id) ;

    VentesDto findByCode(String code) ;

    List<VentesDto> findALl() ;

    void delete(Integer id) ;
}
