package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.Adresse;
import com.bouali.gestiondestock.model.CommandeClient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CommandeClientDto {

    private Integer id ;

    private String code ;

    private Instant dateCommande ;

    private ClientDto client ;

    private Integer idEntreprise ;


    //@JsonIgnore
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
                .idEntreprise(commandeClient.getIdEntreprise())
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
        commandeClient.setClient(ClientDto.toEntity(commandeClientDto.getClient()));
        commandeClient.setIdEntreprise(commandeClientDto.getIdEntreprise());
        return commandeClient;

    }
}
