package com.bouali.gestiondestock.controller;

import com.bouali.gestiondestock.controller.api.CommandeFournisseurApi;
import com.bouali.gestiondestock.dto.CommandeFournisseurDto;
import com.bouali.gestiondestock.services.CommandeFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public void delete(Integer id) {
        commandeFournisseurService.delete(id);
    }
}
