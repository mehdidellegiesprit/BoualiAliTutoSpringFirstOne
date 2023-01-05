package com.bouali.gestiondestock.controller;

import com.bouali.gestiondestock.controller.api.UtilisateurApi;
import com.bouali.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.bouali.gestiondestock.dto.UtilisateurDto;
import com.bouali.gestiondestock.services.FournisseurService;
import com.bouali.gestiondestock.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin("*")
@RestController
public class UtilisateurController implements UtilisateurApi {

    private UtilisateurService utilisateurService ;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService ){
        this.utilisateurService=utilisateurService;
    }

    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        return utilisateurService.save(dto);
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        return utilisateurService.findById(id);
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        return this.utilisateurService.findByEmail(email);
    }

    @Override
    public List<UtilisateurDto> findALl() {
        return utilisateurService.findALl();
    }

    @Override
    public void delete(Integer id) {
        utilisateurService.delete(id);
    }

    @Override
    public UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {
        return utilisateurService.changerMotDePasse(dto);
    }

}
