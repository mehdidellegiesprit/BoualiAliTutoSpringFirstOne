package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.Article;
import com.bouali.gestiondestock.model.TypeMvtStk;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
public class MvtStkDto {
    private Instant dateMvt ;

    private BigDecimal quantite ;

    private ArticleDto article ;

    private TypeMvtStk typeMvt ;
}
