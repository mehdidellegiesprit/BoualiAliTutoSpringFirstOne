package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.Category;
import com.bouali.gestiondestock.model.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ClientDto {

    private Integer id ;

    private String nom ;

    private String prenom ;

    // fix me plz dall
    @JsonIgnore
    private AdresseDto adresse ;

    private String photo ;

    private String mail ;

    private String numTel ;

    @JsonIgnore
    private List<CommandeClientDto> commandeClients ;

    public static ClientDto fromEntity(Client client){
        if (client==null){
            return null ;
            //TODO throw an exception
        }
        //Mapping de Category  => CategoryDto
        return ClientDto.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .photo(client.getPhoto())
                .mail(client.getMail())
                .numTel(client.getMail())
                .build();
    }
    public static Client toEntity(ClientDto clientDto){
        if (clientDto==null){
            return null ;
            //TODO throw an exception
        }
        //Mapping de  CategoryDto => Category

        Client client = new Client() ;
        client.setId(clientDto.getId());
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setPhoto(clientDto.getPhoto());
        client.setMail(clientDto.getMail());
        client.setNumTel(clientDto.getNumTel());
        return client;
    }
}
