package com.bouali.gestiondestock.validator;

import com.bouali.gestiondestock.dto.AdresseDto;
import com.bouali.gestiondestock.dto.CategoryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AdresseValidator {
    public static List<String> validate(AdresseDto adresseDto) {
        List<String> errors = new ArrayList<>() ;
        if ( adresseDto==null){
            errors.add("Veuiller renseigner l'adresse 1 '");
            errors.add("Veuiller renseigner la ville ");
            errors.add("Veuiller renseigner le pays '");
            errors.add("Veuiller renseigner le code postal '");
            return errors;
        }
        if (!StringUtils.hasLength(adresseDto.getAdresse1())) {
            errors.add("Veuiller renseigner l'adresse 1 '");
        }
        if (!StringUtils.hasLength(adresseDto.getVille())) {
            errors.add("Veuiller renseigner la ville ");
        }
        if (!StringUtils.hasLength(adresseDto.getPays())) {
            errors.add("Veuiller renseigner le pays '");
        }
        if (!StringUtils.hasLength(adresseDto.getCodePostale())) {
            errors.add("Veuiller renseigner le code postal '");
        }
        return errors;
    }
}
