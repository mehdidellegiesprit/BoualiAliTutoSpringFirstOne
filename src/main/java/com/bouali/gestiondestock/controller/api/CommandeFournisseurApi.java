package com.bouali.gestiondestock.controller.api;

import com.bouali.gestiondestock.dto.CommandeClientDto;
import com.bouali.gestiondestock.dto.CommandeFournisseurDto;
import com.bouali.gestiondestock.dto.LigneCommandeClientDto;
import com.bouali.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.bouali.gestiondestock.model.EtatCommande;
import com.bouali.gestiondestock.services.CommandeFournisseurService;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.bouali.gestiondestock.utils.Constants.*;


@Api(COMMANDE_FOURNISSEUR_ENDPOINT)
public interface CommandeFournisseurApi {

    @PostMapping(value=CREATE_COMMANDE_FOURNISSEUR_ENDPOINT,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    CommandeFournisseurDto save(@RequestBody CommandeFournisseurDto dto) ;

    @PatchMapping(value=COMMANDE_FOURNISSEUR_ENDPOINT+"/update/etat/{idCommande}/{etatCommande}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeFournisseurDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande) ;

    @PatchMapping(value=COMMANDE_FOURNISSEUR_ENDPOINT+"/update/quantite/{idCommande}/{idLigneCommande}/{quantite}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeFournisseurDto> updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,
                                                             @PathVariable("idLigneCommande") Integer idLigneCommande,
                                                             @PathVariable("quantite") BigDecimal quantite) ;

    @PatchMapping(value=COMMANDE_FOURNISSEUR_ENDPOINT+"/update/fournisseur/{idCommande}/{idFournisseur}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeFournisseurDto> updateFournisseur(@PathVariable("idCommande") Integer idCommande,@PathVariable("idFournisseur") Integer idFournisseur) ;

//    TODO updateArticle
    @PatchMapping(value=COMMANDE_FOURNISSEUR_ENDPOINT+"/update/article/{idCommande}/{idLigneCommande}/{idArticle}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeFournisseurDto> updateArticle (@PathVariable("idCommande") Integer idCommande ,
                                                     @PathVariable("idLigneCommande") Integer idLigneCommande,
                                                     @PathVariable("idArticle") Integer idArticle);


    @DeleteMapping(value=COMMANDE_FOURNISSEUR_ENDPOINT+"/delete/article/{idCommande}/{idLigneCommande}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeFournisseurDto> deleteArticle (@PathVariable("idCommande") Integer idCommande ,
                                                     @PathVariable("idLigneCommande") Integer idLigneCommande);

    @GetMapping(value=FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    CommandeFournisseurDto findById(@PathVariable("idCommandeFournisseur") Integer id) ;

    @GetMapping(value=FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    CommandeFournisseurDto findByCode(@PathVariable("codeCommandeFournisseur") String code) ;

    @GetMapping(value=FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    List<CommandeFournisseurDto> findALl() ;

    @GetMapping(value=COMMANDE_FOURNISSEUR_ENDPOINT+"/lignesCommande/{idCommande}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<LigneCommandeFournisseurDto>> findALlLignesCommandesFournisseurByCommandeFournisseurId(@PathVariable("idCommande") Integer idCommande) ;

    @DeleteMapping(value=DELETE_COMMANDE_FOURNISSEUR_ENDPOINT)
    void delete(@PathVariable("idCommandeFournisseur") Integer id) ;
}
