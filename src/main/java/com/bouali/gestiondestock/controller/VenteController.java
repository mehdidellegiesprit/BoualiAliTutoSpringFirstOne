package com.bouali.gestiondestock.controller;


import com.bouali.gestiondestock.controller.api.VentesApi;
import com.bouali.gestiondestock.dto.VentesDto;
import com.bouali.gestiondestock.services.UtilisateurService;
import com.bouali.gestiondestock.services.VentesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin("*")
@RestController
public class VenteController implements VentesApi {

    private VentesService ventesService ;

    @Autowired
    public VenteController(VentesService ventesService){
        this.ventesService=ventesService;
    }


    @Override
    public VentesDto save(VentesDto dto) {
        return ventesService.save(dto);
    }

    @Override
    public VentesDto findById(Integer id) {
        return ventesService.findById(id);
    }

    @Override
    public VentesDto findByCode(String code) {
        return ventesService.findByCode(code);
    }

    @Override
    public List<VentesDto> findALl() {
        return ventesService.findALl();
    }

    @Override
    public void delete(Integer id) {
        ventesService.delete(id);
    }
}
