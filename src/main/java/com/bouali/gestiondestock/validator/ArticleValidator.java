package com.bouali.gestiondestock.validator;

import com.bouali.gestiondestock.dto.ArticleDto;
import com.bouali.gestiondestock.dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ArticleValidator {
    public static List<String> validate(ArticleDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto==null) {
            errors.add("veiller renseigner le code  de l'article ");
            errors.add("veiller renseigner la designation de l'article ");
            errors.add("veiller renseigner le prix unitaire de l'article ");
            errors.add("veiller renseigner le taux TVA de l'article ");
            errors.add("veiller renseigner le prix unitaire Ttc de l'article ");
            errors.add("veiller renseigner la categorie de l'article ");
            return errors ;
        }


        if (!StringUtils.hasLength(dto.getCodeArticle())){
            errors.add("veiller renseigner le code  de l'article ");
        }

        if (!StringUtils.hasLength(dto.getDesignation())){
            errors.add("veiller renseigner la designation de l'article ");
        }
        if ((dto.getPrixUnitaireHt())==null){
            errors.add("veiller renseigner le prix unitaire de l'article ");
        }
        if ((dto.getTauxTva())==null){
            errors.add("veiller renseigner le taux TVA de l'article ");
        }
        if ((dto.getPrixUnitaireTtc())==null){
            errors.add("veiller renseigner le prix unitaire Ttc de l'article ");
        }
        if (dto.getCategory()==null || dto.getCategory().getId()==-1){
            errors.add("veiller renseigner la categorie de l'article ");
        }


        return errors ;
    }
}
