package com.bouali.gestiondestock.services.impl;

import com.bouali.gestiondestock.Repository.CategoryRepository;
import com.bouali.gestiondestock.Repository.ClientRepository;
import com.bouali.gestiondestock.dto.CategoryDto;
import com.bouali.gestiondestock.dto.ClientDto;
import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidEntityException;
import com.bouali.gestiondestock.model.Category;
import com.bouali.gestiondestock.model.Client;
import com.bouali.gestiondestock.services.ClientService;
import com.bouali.gestiondestock.validator.CategoryValidator;
import com.bouali.gestiondestock.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository ;

    @Autowired //injection des dependace par constructeur
    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository=clientRepository;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        List<String> errors = ClientValidator.validate(dto) ;
        if(!errors.isEmpty()){
            log.error("Client is not valid{} ",dto) ;
            throw new InvalidEntityException("Le client n'est pas valide", ErrorCodes.CLIENT_NOT_VALID,errors);
        }
        return ClientDto.fromEntity(
                clientRepository.save(ClientDto.toEntity(dto))
        );
    }

    @Override
    public ClientDto findById(Integer id) {
        if(id==null){
            log.error("Client ID is null") ;
            return null ;
        }
        Optional<Client> client = clientRepository.findById(id);
        return Optional.of(ClientDto.fromEntity(client.get())).orElseThrow(()->
                new EntityNotFoundException(
                        "Aucun Client avec l'ID = "+ id + "n'ete trouve dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND
                )
        );
    }

    @Override
    public List<ClientDto> findALl() {
        return clientRepository.findAll()
                .stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("Client ID is null") ;
            return;
        }
        clientRepository.deleteById(id);
    }
}
