package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.LigneVente;
import com.bouali.gestiondestock.model.MvtStk;
import com.bouali.gestiondestock.model.SourceMvtStk;
import com.bouali.gestiondestock.model.TypeMvtStk;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.Instant;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MvtStkDto {

    private Integer id ;

    private Instant dateMvt ;

    private BigDecimal quantite ;

    // fix me plz dall
    //@JsonIgnore
    private ArticleDto article ;

    private SourceMvtStk sourceMvt ;

    private TypeMvtStk typeMvt ;

    private Integer idEntreprise ;

    public static MvtStkDto fromEntity (MvtStk mvtStk){

        if (mvtStk==null){
            return null ;
        }
        return MvtStkDto.builder()
                .id(mvtStk.getId())
                .dateMvt(mvtStk.getDateMvt())
                .quantite(mvtStk.getQuantite())
                .article(ArticleDto.fromEntity(mvtStk.getArticle()))
                .sourceMvt(mvtStk.getSourceMvt())
                .typeMvt(mvtStk.getTypeMvt())
                .idEntreprise(mvtStk.getIdEntreprise())
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
        mvtStk.setArticle(ArticleDto.toEntity(mvtStkDto.getArticle()));
        mvtStk.setSourceMvt(mvtStkDto.getSourceMvt());
        mvtStk.setTypeMvt(mvtStkDto.getTypeMvt());
        mvtStk.setIdEntreprise(mvtStkDto.getIdEntreprise());

        return mvtStk;
    }
}
