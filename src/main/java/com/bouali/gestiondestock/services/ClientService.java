package com.bouali.gestiondestock.services;

import com.bouali.gestiondestock.dto.CategoryDto;
import com.bouali.gestiondestock.dto.ClientDto;

import java.util.List;

public interface ClientService {
    ClientDto save(ClientDto dto) ;

    ClientDto findById(Integer id) ;

    List<ClientDto> findALl() ;

    void delete(Integer id) ;
}
