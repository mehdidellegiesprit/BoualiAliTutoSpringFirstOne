package com.bouali.gestiondestock.validator;
import com.bouali.gestiondestock.dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class UtilisateurValidator {
    public static List<String> validate(UtilisateurDto utilisateurDto) {
        List<String> errors = new ArrayList<>() ;

        if (utilisateurDto==null){
            errors.add("Veuiller renseigner le nom d'utilisteur");
            errors.add("Veuiller renseigner le prenom d'utilisteur");
            errors.add("Veuiller renseigner le mot de passe  d'utilisteur");
            errors.add("Veuiller renseigner l'adresse d'utilisteur");
            errors.add("Veuiller renseigner l'email d'utilisteur");
            errors.add("Veuiller renseigner la date de naissance  d'utilisteur");
            errors.add("Le champ pays est obligatoire");
            errors.addAll(AdresseValidator.validate(null)) ;
            return errors ;
        }

        if (!StringUtils.hasLength(utilisateurDto.getNom())){
            errors.add("Veuiller renseigner le nom d'utilisteur");
        }
        if (!StringUtils.hasLength(utilisateurDto.getPrenom())){
            errors.add("Veuiller renseigner le prenom d'utilisteur");
        }
        if (!StringUtils.hasLength(utilisateurDto.getEmail())){
            errors.add("Veuiller renseigner l'email d'utilisteur");
        }
        if (!StringUtils.hasLength(utilisateurDto.getMotDePasse())){
            errors.add("Veuiller renseigner le mot de passe  d'utilisteur");
        }
        if (utilisateurDto.getDateDeNaissance()==null){
            errors.add("Veuiller renseigner la date de naissance  d'utilisteur");
        }

        errors.addAll(AdresseValidator.validate(utilisateurDto.getAdresse())) ;
        return errors;
    }
}

