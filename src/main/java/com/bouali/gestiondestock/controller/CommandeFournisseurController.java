package com.bouali.gestiondestock.controller;

import com.bouali.gestiondestock.controller.api.CommandeFournisseurApi;
import com.bouali.gestiondestock.dto.CommandeClientDto;
import com.bouali.gestiondestock.dto.CommandeFournisseurDto;
import com.bouali.gestiondestock.dto.LigneCommandeClientDto;
import com.bouali.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.bouali.gestiondestock.model.EtatCommande;
import com.bouali.gestiondestock.services.CommandeFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
@CrossOrigin("*")
@RestController
public class CommandeFournisseurController implements CommandeFournisseurApi {


    private CommandeFournisseurService commandeFournisseurService ;

    @Autowired
    public CommandeFournisseurController(CommandeFournisseurService commandeFournisseurService ){
        this.commandeFournisseurService=commandeFournisseurService;
    }


    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        return commandeFournisseurService.save(dto);
    }

    @Override
    public ResponseEntity<CommandeFournisseurDto> updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        System.out.println("updateEtatCommande Fournisseur d5alnaaaa *****************");
        return ResponseEntity.ok(commandeFournisseurService.updateEtatCommande(idCommande,etatCommande));
    }

    @Override
    public ResponseEntity<CommandeFournisseurDto> updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        return ResponseEntity.ok(commandeFournisseurService.updateQuantiteCommande(idCommande,idLigneCommande,quantite));
    }

    @Override
    public ResponseEntity<CommandeFournisseurDto> updateFournisseur(Integer idCommande, Integer idFournisseur) {
        return ResponseEntity.ok(commandeFournisseurService.updateFournisseur(idCommande,idFournisseur));
    }

    @Override
    public ResponseEntity<CommandeFournisseurDto> updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        return ResponseEntity.ok(commandeFournisseurService.updateArticle(idCommande,idLigneCommande,idArticle)) ;
    }

    @Override
    public ResponseEntity<CommandeFournisseurDto> deleteArticle(Integer idCommande, Integer idLigneCommande) {
        return ResponseEntity.ok(commandeFournisseurService.deleteArticle(idCommande,idLigneCommande)) ;
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        return commandeFournisseurService.findById(id);
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        return commandeFournisseurService.findByCode(code);
    }

    @Override
    public List<CommandeFournisseurDto> findALl() {
        return commandeFournisseurService.findALl();
    }

    @Override
    public ResponseEntity<List<LigneCommandeFournisseurDto>> findALlLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande) {
        return ResponseEntity.ok(commandeFournisseurService.findALlLigneCommandesFournisseurByCommandeFournisseurId_service(idCommande));
    }

    @Override
    public void delete(Integer id) {
        commandeFournisseurService.delete(id);
    }
}
