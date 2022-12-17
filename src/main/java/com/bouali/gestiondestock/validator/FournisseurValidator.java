package com.bouali.gestiondestock.validator;

import com.bouali.gestiondestock.dto.ClientDto;
import com.bouali.gestiondestock.dto.FournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {
    public static List<String> validate(FournisseurDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto==null){
            errors.add("veiller renseigner le nom du fournisseur ");
            errors.add("veiller renseigner le prenom du fournisseur ");
            errors.add("veiller renseigner la photo du fournisseur ");
            errors.add("veiller renseigner mail du fournisseur ");
            errors.add("veiller renseigner le num Tel du fournisseur ");
            errors.addAll(AdresseValidator.validate(null)) ;
            return errors ;
        }

        if (!StringUtils.hasLength(dto.getNom())){
            errors.add("veiller renseigner le nom du fournisseur ");
        }
        if (!StringUtils.hasLength(dto.getPrenom())){
            errors.add("veiller renseigner le prenom du fournisseur ");
        }
        if (!StringUtils.hasLength(dto.getMail())){
            errors.add("veiller renseigner mail du fournisseur ");
        }
        if (!StringUtils.hasLength(dto.getNumTel())){
            errors.add("veiller renseigner le num Tel du fournisseur ");
        }
        errors.addAll(AdresseValidator.validate(dto.getAdresse()))  ;
        return errors ;
    }
}
