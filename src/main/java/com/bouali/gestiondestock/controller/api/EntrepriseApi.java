package com.bouali.gestiondestock.controller.api;


import com.bouali.gestiondestock.dto.EntrepriseDto;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bouali.gestiondestock.utils.Constants.ENTREPRISE_ENDPOINT;
import static com.bouali.gestiondestock.utils.Constants.FOURNISSEUR_ENDPOINT;

@Api(ENTREPRISE_ENDPOINT)
public interface EntrepriseApi {

    @PostMapping(value=ENTREPRISE_ENDPOINT+"/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    EntrepriseDto save(@RequestBody EntrepriseDto dto) ;

    @GetMapping(value=ENTREPRISE_ENDPOINT+"/{idEntreprise}",produces = MediaType.APPLICATION_JSON_VALUE)
    EntrepriseDto findById(@PathVariable("idEntreprise") Integer id) ;

    @GetMapping(value=ENTREPRISE_ENDPOINT+"/all",produces = MediaType.APPLICATION_JSON_VALUE)
    List<EntrepriseDto> findALl() ;

    @DeleteMapping(value=ENTREPRISE_ENDPOINT+"/delete/{idEntreprise}")
    void delete(@PathVariable("idEntreprise") Integer id) ;
}
