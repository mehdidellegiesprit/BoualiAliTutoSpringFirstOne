package com.bouali.gestiondestock.controller;

import com.bouali.gestiondestock.controller.api.ClientApi;
import com.bouali.gestiondestock.dto.ClientDto;
import com.bouali.gestiondestock.services.CategoryService;
import com.bouali.gestiondestock.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController implements ClientApi {

    private ClientService clientService;


    @Autowired
    public ClientController(ClientService clientService){
        this.clientService=clientService;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        return clientService.save(dto);
    }

    @Override
    public ClientDto findById(Integer id) {
        return clientService.findById(id);
    }

    @Override
    public List<ClientDto> findALl() {
        return clientService.findALl();
    }

    @Override
    public void delete(Integer id) {
        clientService.delete(id);
    }
}
