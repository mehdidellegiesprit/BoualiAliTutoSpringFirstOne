package com.bouali.gestiondestock.services;

import com.bouali.gestiondestock.dto.ArticleDto;
import com.bouali.gestiondestock.dto.LigneCommandeClientDto;
import com.bouali.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.bouali.gestiondestock.dto.LigneVenteDto;

import java.util.List;

public interface ArticleService {
    ArticleDto save(ArticleDto dto) ;
    ArticleDto findById(Integer id) ;
    ArticleDto findByCodeArticle(String codeArticle) ;
    List<ArticleDto> findALl() ;
    List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) ;
    List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) ;
    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle) ;
    List<ArticleDto> findArticleByIdCategory(Integer idCategory) ;

    void delete(Integer id) ;

}
