package com.bouali.gestiondestock.controller.api;

import com.bouali.gestiondestock.dto.CommandeClientDto;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bouali.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT+"/commandesclients")
public interface CommandeClientApi {

    @PostMapping(value=APP_ROOT+"/commandesclients/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto dto) ;

    @GetMapping(value=APP_ROOT+"/commandesclients/{idCommandeClient}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeClientDto> findById(@PathVariable Integer idCommandeClient) ;

    @GetMapping(value=APP_ROOT+"/commandesclients/{codeCommandeClient}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeClientDto> findByCode(@PathVariable("codeCommandeClient") String code) ;
    @GetMapping(value=APP_ROOT+"/commandesclients/all",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CommandeClientDto>> findALl() ;
    @DeleteMapping(value=APP_ROOT+"/commandesclients/delete/{idCommandeClient}")
    ResponseEntity delete(@PathVariable("idCommandeClient") Integer id) ;
}
