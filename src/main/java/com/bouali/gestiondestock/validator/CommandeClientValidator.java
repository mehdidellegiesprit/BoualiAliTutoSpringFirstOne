package com.bouali.gestiondestock.validator;

import com.bouali.gestiondestock.dto.CommandeClientDto;
import com.bouali.gestiondestock.dto.EntrepriseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeClientValidator {

    //TODO to BE IMPLEMENTED
    public static List<String> validate(CommandeClientDto dto) {
        List<String> errors = new ArrayList<>();
        if (dto==null){
            errors.add("Veuiller renseigner le code de la commande ") ;
            errors.add("Veuiller renseigner la date  de la commande ") ;
            errors.add("Veuiller renseigner l'etat de la commande ") ;
            errors.add("Veuiller renseigner le client ") ;
            return errors ;
        }
        if (!StringUtils.hasLength(dto.getCode())){
            errors.add("Veuiller renseigner le code de la commande ") ;
        }
        if (dto.getDateCommande()==null){
            errors.add("Veuiller renseigner la date  de la commande ") ;
        }
        if (!StringUtils.hasLength(dto.getEtatCommande().toString())){
            errors.add("Veuiller renseigner l'etat de la commande ") ;
        }
        if (dto.getClient()==null || dto.getClient().getId()==null){
            errors.add("Veuiller renseigner le client ") ;
        }
        return errors ;
    }
}
