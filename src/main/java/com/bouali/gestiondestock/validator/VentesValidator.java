package com.bouali.gestiondestock.validator;

import com.bouali.gestiondestock.dto.LigneCommandeClientDto;
import com.bouali.gestiondestock.dto.VentesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VentesValidator {
    //TODO to BE IMPLEMENTED
    public static List<String> validate(VentesDto dto) {
        List<String> errors = new ArrayList<>();
        if (dto==null){
            errors.add("Veuiller renseigner le code de la commande ") ;
            errors.add("Veuiller renseigner la date  de la commande ") ;
            return errors ;
        }
        if (!StringUtils.hasLength(dto.getCode())){
            errors.add("Veuiller renseigner le code de la commande ") ;
        }
        if (dto.getDateVente()==null){
            errors.add("Veuiller renseigner la date  de la commande ") ;
        }

        return errors ;
    }
}
