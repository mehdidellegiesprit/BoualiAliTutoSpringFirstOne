package com.bouali.gestiondestock.validator;

import com.bouali.gestiondestock.dto.ClientDto;
import com.bouali.gestiondestock.dto.EntrepriseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EntrepriseValidator {

    public static List<String> validate(EntrepriseDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto==null){
            errors.add("veiller renseigner le nom de l'entreprise ");
            errors.add("veiller renseigner la description de l'entreprise ");
            errors.add("veiller renseigner la photo du l'entreprise ");
            errors.add("veiller renseigner mail du l'entreprise ");
            errors.add("veiller renseigner le num Tel du l'entreprise ");
            return errors ;
        }

        if (!StringUtils.hasLength(dto.getNom())){
            errors.add("veiller renseigner le nom de l'entreprise ");
        }
        if (!StringUtils.hasLength(dto.getDescription())){
            errors.add("veiller renseigner la description de l'entreprise ");
        }
        if (!StringUtils.hasLength(dto.getPhoto())){
            errors.add("veiller renseigner la photo du l'entreprise ");
        }
        if (!StringUtils.hasLength(dto.getEmail())){
            errors.add("veiller renseigner mail du l'entreprise ");
        }
        if (!StringUtils.hasLength(dto.getNumTel())){
            errors.add("veiller renseigner le num Tel du l'entreprise ");
        }
        return errors ;
    }
}
