package com.bouali.gestiondestock.services;

import com.bouali.gestiondestock.dto.ArticleDto;
import com.bouali.gestiondestock.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto save(CategoryDto dto) ;

    CategoryDto findById(Integer id) ;

    CategoryDto findByCode(String codeCategory) ;

    List<CategoryDto> findALl() ;

    void delete(Integer id) ;
}
