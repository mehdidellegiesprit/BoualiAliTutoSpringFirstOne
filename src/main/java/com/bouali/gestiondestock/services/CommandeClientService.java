package com.bouali.gestiondestock.services;

import com.bouali.gestiondestock.dto.ArticleDto;
import com.bouali.gestiondestock.dto.CommandeClientDto;

import java.util.List;

public interface CommandeClientService {

    CommandeClientDto save(CommandeClientDto dto) ;

    CommandeClientDto findById(Integer id) ;

    CommandeClientDto findByCode(String code) ;

    List<CommandeClientDto> findALl() ;

    void delete(Integer id) ;
}
