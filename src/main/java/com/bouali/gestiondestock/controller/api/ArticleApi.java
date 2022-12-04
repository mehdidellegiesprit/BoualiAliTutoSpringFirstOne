package com.bouali.gestiondestock.controller.api;

import com.bouali.gestiondestock.dto.ArticleDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

import static com.bouali.gestiondestock.utils.Constants.APP_ROOT;



public interface ArticleApi {


    @PostMapping(value=APP_ROOT+"/articles/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto save(@RequestBody ArticleDto dto) ;
    // @RequestBody : transformer le json dans la reqquette body a un objet de type Article Dto

    @GetMapping(value=APP_ROOT+"/articles/{idArticle}",produces = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto findById(@PathVariable("idArticle") Integer id) ;

    @GetMapping(value=APP_ROOT+"/articles/{codeArticle}",produces = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle) ;

    @GetMapping(value=APP_ROOT+"/articles/all",produces = MediaType.APPLICATION_JSON_VALUE)
    List<ArticleDto> findALl() ;


    @DeleteMapping(value=APP_ROOT+"/articles/delete/{idArticle}")
    void delete(@PathVariable("idArticle")Integer id) ;
}
