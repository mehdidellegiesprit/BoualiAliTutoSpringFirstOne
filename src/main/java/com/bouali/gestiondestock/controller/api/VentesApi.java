package com.bouali.gestiondestock.controller.api;


import com.bouali.gestiondestock.dto.VentesDto;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bouali.gestiondestock.utils.Constants.UTILISATEUR_ENDPOINT;
import static com.bouali.gestiondestock.utils.Constants.VENTES_ENDPOINT;

@Api(VENTES_ENDPOINT)
public interface VentesApi {

    @PostMapping(value=VENTES_ENDPOINT+"/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    VentesDto save(@RequestBody VentesDto dto) ;


    @GetMapping(value=VENTES_ENDPOINT+"/{idVente}",produces = MediaType.APPLICATION_JSON_VALUE)
    VentesDto findById(@PathVariable("idVente")Integer id) ;

    @GetMapping(value=VENTES_ENDPOINT+"/{codeVente}",produces = MediaType.APPLICATION_JSON_VALUE)
    VentesDto findByCode(@PathVariable("codeVente") String code) ;


    @GetMapping(value=VENTES_ENDPOINT+"/all",produces = MediaType.APPLICATION_JSON_VALUE)
    List<VentesDto> findALl() ;

    @DeleteMapping(value=UTILISATEUR_ENDPOINT+"/delete/{idVente}")
    void delete(@PathVariable("idVente") Integer id) ;
}
