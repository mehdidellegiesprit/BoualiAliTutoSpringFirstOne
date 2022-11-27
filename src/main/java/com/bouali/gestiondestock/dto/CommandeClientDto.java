package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.Adresse;
import com.bouali.gestiondestock.model.CommandeClient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;


import java.time.Instant;
import java.util.List;

@Builder
@Data
public class CommandeClientDto {

    private Integer id ;

    private String code ;

    private Instant dateCommande ;

    // fix me plz dall
    //@JsonIgnore
    private ClientDto client ;


    // fix me plz dall
    @JsonIgnore
    private List<LigneCommandeClientDto> ligneCommandeClients ;

    public static CommandeClientDto fromEntity (CommandeClient commandeClient){

        if (commandeClient==null){
            return null ;
        }
        return CommandeClientDto.builder()
                .id(commandeClient.getId())
                .code(commandeClient.getCode())
                .dateCommande(commandeClient.getDateCommande())
                .client(ClientDto.fromEntity(commandeClient.getClient()))
                .build();
    }

    public static CommandeClient toEntity (CommandeClientDto commandeClientDto){
        if (commandeClientDto==null){
            return null ;
        }
        CommandeClient commandeClient = new CommandeClient() ;
        commandeClient.setId(commandeClientDto.getId());
        commandeClient.setCode(commandeClientDto.getCode());
        commandeClient.setDateCommande(commandeClientDto.getDateCommande());
        //        client.setAdresse(AdresseDto.toEntity(clientDto.getAdresse()));
        commandeClient.setClient(ClientDto.toEntity(commandeClientDto.getClient()));
        return commandeClient;

    }
}
