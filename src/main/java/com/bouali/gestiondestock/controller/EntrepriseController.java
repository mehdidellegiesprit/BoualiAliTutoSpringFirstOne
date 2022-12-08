package com.bouali.gestiondestock.controller;

import com.bouali.gestiondestock.controller.api.EntrepriseApi;
import com.bouali.gestiondestock.dto.EntrepriseDto;
import com.bouali.gestiondestock.services.EntrepriseService;
import com.bouali.gestiondestock.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class EntrepriseController implements EntrepriseApi {

    private EntrepriseService entrepriseService ;

    @Autowired
    public EntrepriseController(EntrepriseService entrepriseService ){
        this.entrepriseService=entrepriseService;
    }

    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        return entrepriseService.save(dto);
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        return entrepriseService.findById(id);
    }

    @Override
    public List<EntrepriseDto> findALl() {
        return entrepriseService.findALl();
    }

    @Override
    public void delete(Integer id) {
        entrepriseService.delete(id);
    }
}
