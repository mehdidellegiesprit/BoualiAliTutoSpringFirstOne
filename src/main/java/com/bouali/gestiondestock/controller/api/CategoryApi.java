package com.bouali.gestiondestock.controller.api;

import com.bouali.gestiondestock.dto.ArticleDto;
import com.bouali.gestiondestock.dto.CategoryDto;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bouali.gestiondestock.utils.Constants.APP_ROOT;
@CrossOrigin(origins = "http://localhost:4200")
@Api(APP_ROOT+"/categories")
public interface CategoryApi {
    @PostMapping(value=APP_ROOT+"/categories/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value=" Enregsitrer une categories" , notes = "Cette methode permet d'enregistrer ou modifier une categorie ",response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200,message = "L'objet category cree / modifier "),
            @ApiResponse(code=400,message = "L'objet category n'est pas valide ")
    })
    CategoryDto save(@RequestBody CategoryDto dto) ;

    @GetMapping(value=APP_ROOT+"/categories/{idCategory}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value=" Rechercher une category par ID" , notes = "Cette methode permet de chercher une categorie par son ID  ",response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200,message = "La categorie  a ete trouve dans la BDD "),
            @ApiResponse(code=404,message = "Aucun categorie n'existe dans la BDD avec l'ID fourni")
    })
    CategoryDto findById(@PathVariable("idCategory") Integer idCategory) ;

    @GetMapping(value=APP_ROOT+"/categories/{codeCategory}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value=" Rechercher une categorie par CODE" , notes = "Cette methode permet de chercher une categorie par son CODE  ",response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200,message = "La categorie a ete trouve dans la BDD "),
            @ApiResponse(code=404,message = "Aucun categorie n'existe dans la BDD avec le CODE fourni")
    })
    CategoryDto findByCode( @PathVariable("codeCategory")  String codeCategory) ;

    @GetMapping(value=APP_ROOT+"/categories/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value=" Renvoi la liste des categories " , notes = "Cette methode permet de chercher et renvoyer la listes des categories qui existent dans la BDD",
            responseContainer = "List<CategoryDto>")
    @ApiResponses(value = {
            @ApiResponse(code=200,message = "La liste des CategoryDto / une liste vide ")
    })
    List<CategoryDto> findALl() ;

    @DeleteMapping(value=APP_ROOT+"/categories/delete/{idCategory}")
    @ApiOperation(value=" Supprimer une category" , notes = "Cette methode permet de supprimer une categorie par ID",response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200,message = "La categorie a ete supprimer")
    })
    void delete(@PathVariable("idCategory") Integer id) ;
}
