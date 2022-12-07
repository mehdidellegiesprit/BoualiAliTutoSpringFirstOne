package com.bouali.gestiondestock.controller.api;

import com.bouali.gestiondestock.dto.ArticleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

import static com.bouali.gestiondestock.utils.Constants.APP_ROOT;


@Api(APP_ROOT+"/articles")
public interface ArticleApi {
    @PostMapping(value=APP_ROOT+"/articles/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value=" Enregsitrer un article" , notes = "Cette methode permet d'enregistrer ou modifier un article ",response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200,message = "L'objet article cree / modifier "),
            @ApiResponse(code=400,message = "L'objet article n'est pas valide ")
    })
    ArticleDto save(@RequestBody ArticleDto dto) ;
    // @RequestBody : transformer le json dans la reqquette body a un objet de type Article Dto

    @GetMapping(value=APP_ROOT+"/articles/{idArticle}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value=" Rechercher un article par ID" , notes = "Cette methode permet de chercher un article par son ID  ",response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200,message = "L'article a ete trouve dans la BDD "),
            @ApiResponse(code=404,message = "Aucun article n'existe dans la BDD avec l'ID fourni")
    })
    ArticleDto findById(@PathVariable("idArticle") Integer id) ;

    @GetMapping(value=APP_ROOT+"/articles/{codeArticle}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value=" Rechercher un article par CODE" , notes = "Cette methode permet de chercher un article par son CODE  ",response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200,message = "L'article a ete trouve dans la BDD "),
            @ApiResponse(code=404,message = "Aucun article n'existe dans la BDD avec le CODE fourni")
    })
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle) ;

    @GetMapping(value=APP_ROOT+"/articles/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value=" Renvoi la liste des articles " , notes = "Cette methode permet de chercher et renvoyer la listes des articles qui existent dans la BDD",
            responseContainer = "List<ArticleDto>")
    @ApiResponses(value = {
            @ApiResponse(code=200,message = "La liste des articles / une liste vide ")
    })
    List<ArticleDto> findALl() ;


    @DeleteMapping(value=APP_ROOT+"/articles/delete/{idArticle}")
    @ApiOperation(value=" Supprimer un article" , notes = "Cette methode permet de supprimer un article par ID",response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200,message = "L'article a ete supprimer")
    })
    void delete(@PathVariable("idArticle")Integer id) ;
}
