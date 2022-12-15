package com.bouali.gestiondestock.controller.api;

import com.bouali.gestiondestock.dto.CommandeClientDto;
import com.bouali.gestiondestock.dto.LigneCommandeClientDto;
import com.bouali.gestiondestock.model.EtatCommande;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.bouali.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT+"/commandesclients")
public interface CommandeClientApi {

    @PostMapping(value=APP_ROOT+"/commandesclients/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto dto) ;

    @PatchMapping(value=APP_ROOT+"/commandesclients/update/etat/{idCommande}/{etatCommande}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeClientDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande,@PathVariable("etatCommande")  EtatCommande etatCommande) ;

    @PatchMapping(value=APP_ROOT+"/commandesclients/update/quantite/{idCommande}/{idLigneCommande}/{quantite}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeClientDto> updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,
                                                             @PathVariable("idLigneCommande") Integer idLigneCommande,
                                                             @PathVariable("quantite") BigDecimal quantite) ;


    @PatchMapping(value=APP_ROOT+"/commandesclients/update/client/{idCommande}/{idClient}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeClientDto> updateClient(@PathVariable("idCommande") Integer idCommande,@PathVariable("idClient") Integer idClient) ;

    @PatchMapping(value=APP_ROOT+"/commandesclients/update/article/{idCommande}/{idLigneCommande}/{idArticle}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeClientDto> updateArticle (@PathVariable("idCommande") Integer idCommande ,
                                                     @PathVariable("idLigneCommande") Integer idLigneCommande,
                                                     @PathVariable("idArticle") Integer idArticle);


    @DeleteMapping(value=APP_ROOT+"/commandesclients/delete/article/{idCommande}/{idLigneCommande}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeClientDto> deleteArticle (@PathVariable("idCommande") Integer idCommande ,
                                                     @PathVariable("idLigneCommande") Integer idLigneCommande);

    @GetMapping(value=APP_ROOT+"/commandesclients/{idCommandeClient}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeClientDto> findById(@PathVariable Integer idCommandeClient) ;

    @GetMapping(value=APP_ROOT+"/commandesclients/{codeCommandeClient}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeClientDto> findByCode(@PathVariable("codeCommandeClient") String code) ;
    @GetMapping(value=APP_ROOT+"/commandesclients/all",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CommandeClientDto>> findALl() ;

    @GetMapping(value=APP_ROOT+"/commandesclients/lignesCommande/{idCommande}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<LigneCommandeClientDto>> findALlLignesCommandesClientByCommandeClientId(@PathVariable("idCommande") Integer idCommande) ;

    @DeleteMapping(value=APP_ROOT+"/commandesclients/delete/{idCommandeClient}")
    ResponseEntity <Void> delete(@PathVariable("idCommandeClient") Integer id) ;
}
