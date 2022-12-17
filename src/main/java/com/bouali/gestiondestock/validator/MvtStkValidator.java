package com.bouali.gestiondestock.validator;

import com.bouali.gestiondestock.dto.CategoryDto;
import com.bouali.gestiondestock.dto.MvtStkDto;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MvtStkValidator {
    public static List<String> validate(MvtStkDto dto) {
        List<String> errors = new ArrayList<>() ;
        if (dto==null){
            errors.add("veiller renseigner la date du mouvement ");
            errors.add("veiller renseigner la quantite  du mouvement ");
            errors.add("veiller renseigner l'article");
            errors.add("veiller renseigner le type du mouvement ");
            return errors ;
        }
        if (dto.getDateMvt()==null) {
            errors.add("veiller renseigner la date du mouvement ");
        }
        if (dto.getQuantite()==null ||dto.getQuantite().compareTo(BigDecimal.ZERO)==0) {
            errors.add("veiller renseigner la quantite  du mouvement ");

        }
        if (dto.getArticle()==null || dto.getArticle().getId()==null) {
            errors.add("veiller renseigner l'article");
        }
        if (!StringUtils.hasLength(dto.getTypeMvt().name())) {
            errors.add("veiller renseigner le type du mouvement ");
        }

        return errors;
    }
}
