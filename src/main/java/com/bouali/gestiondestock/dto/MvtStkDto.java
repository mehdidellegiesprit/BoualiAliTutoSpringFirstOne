package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.LigneVente;
import com.bouali.gestiondestock.model.MvtStk;
import com.bouali.gestiondestock.model.TypeMvtStk;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
public class MvtStkDto {

    private Integer id ;

    private Instant dateMvt ;

    private BigDecimal quantite ;

    // fix me plz dall
    @JsonIgnore
    private ArticleDto article ;

    private TypeMvtStk typeMvt ;

    public static MvtStkDto fromEntity (MvtStk mvtStk){

        if (mvtStk==null){
            return null ;
        }
        return MvtStkDto.builder()
                .id(mvtStk.getId())
                .dateMvt(mvtStk.getDateMvt())
                .quantite(mvtStk.getQuantite())
                .typeMvt(mvtStk.getTypeMvt())
                .build();
    }

    public static MvtStk toEntity (MvtStkDto mvtStkDto){
        if (mvtStkDto==null){
            return null ;
        }
        MvtStk mvtStk = new MvtStk() ;
        mvtStk.setId(mvtStkDto.getId());
        mvtStk.setDateMvt(mvtStkDto.getDateMvt());
        mvtStk.setQuantite(mvtStkDto.getQuantite());
        mvtStk.setTypeMvt(mvtStkDto.getTypeMvt());
        return mvtStk;
    }
}
