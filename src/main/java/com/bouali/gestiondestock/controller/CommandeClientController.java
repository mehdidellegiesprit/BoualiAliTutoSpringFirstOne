package com.bouali.gestiondestock.controller;

import com.bouali.gestiondestock.controller.api.CommandeClientApi;
import com.bouali.gestiondestock.dto.CommandeClientDto;
import com.bouali.gestiondestock.services.CommandeClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CommandeClientController implements CommandeClientApi {

    private CommandeClientService commandeClientService ;

    @Autowired
    public CommandeClientController(CommandeClientService commandeClientService){
        this.commandeClientService=commandeClientService;
    }


    @Override
    public ResponseEntity<CommandeClientDto> save(CommandeClientDto dto) {
        return ResponseEntity.ok(commandeClientService.save(dto));
    }

    @Override
    public ResponseEntity<CommandeClientDto> findById(Integer id) {
        return ResponseEntity.ok(commandeClientService.findById(id));
    }

    @Override
    public ResponseEntity<CommandeClientDto> findByCode(String code) {
        return ResponseEntity.ok(commandeClientService.findByCode(code));
    }

    @Override
    public ResponseEntity<List<CommandeClientDto>> findALl() {
        return ResponseEntity.ok(commandeClientService.findALl());
    }

    @Override
    public ResponseEntity delete(Integer id) {
        commandeClientService.delete(id);
        return ResponseEntity.ok().build();
    }
}
