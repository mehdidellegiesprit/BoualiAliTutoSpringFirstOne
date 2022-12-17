package com.bouali.gestiondestock.services.impl;

import com.bouali.gestiondestock.Repository.MvtStkRepository;
import com.bouali.gestiondestock.dto.MvtStkDto;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidEntityException;
import com.bouali.gestiondestock.model.TypeMvtStk;
import com.bouali.gestiondestock.services.ArticleService;
import com.bouali.gestiondestock.services.MvtStkService;
import com.bouali.gestiondestock.validator.MvtStkValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MvtStkServiceImpl implements MvtStkService {

    private MvtStkRepository repository ;
    private ArticleService articleService;

    @Autowired
    public MvtStkServiceImpl(MvtStkRepository repository, ArticleService articleService) {
        this.repository = repository;
        this.articleService = articleService;
    }

    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        if (idArticle==null){
            log.warn("ID article is NULL");
            return BigDecimal.valueOf(-1);
        }
        articleService.findById(idArticle);
        return repository.stockReelArticle(idArticle);
    }

    @Override
    public List<MvtStkDto> mvtStkArticle(Integer idArticle) {
        return repository.findAllByArticleId(idArticle).stream()
                .map(MvtStkDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MvtStkDto entreeStock(MvtStkDto dto) {
        //TODO ajouter/modifier une ENTREE de stock quantie ==> + TypeMvtStk = entree
        return entreePositive(dto,TypeMvtStk.ENTREE);
    }

    @Override
    public MvtStkDto sortieStock(MvtStkDto dto) {
        //TODO ajouter/modifier une SORTIE de stock quantie ==> + TypeMvtStk = entree
        return sortieNegative(dto,TypeMvtStk.SORTIE);
    }

    @Override
    public MvtStkDto correctionStockPos(MvtStkDto dto) {
        return entreePositive(dto,TypeMvtStk.CORRECTION_POS);
    }

    @Override
    public MvtStkDto correctionStockNeg(MvtStkDto dto) {
        return sortieNegative(dto,TypeMvtStk.CORRECTION_NEG);
    }


    public MvtStkDto entreePositive(MvtStkDto dto,TypeMvtStk typeMvtStk) {
        List<String> errors = MvtStkValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("mouvement de stock  is not valid{} ",dto) ;
            throw new InvalidEntityException("Le mouvement de stock n'est pas valide", ErrorCodes.Mvt_Stk_NOT_VALID,errors);
        }
        dto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantite().doubleValue())
                )
        );
        dto.setTypeMvt(typeMvtStk);
        return MvtStkDto.fromEntity(
                repository.save(MvtStkDto.toEntity(dto))
        );
    }

    public MvtStkDto sortieNegative(MvtStkDto dto,TypeMvtStk typeMvtStk) {
        List<String> errors = MvtStkValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("mouvement de stock  is not valid{} ",dto) ;
            throw new InvalidEntityException("Le mouvement de stock n'est pas valide", ErrorCodes.Mvt_Stk_NOT_VALID,errors);
        }
        dto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantite().doubleValue()) * -1
                )
        );
        dto.setTypeMvt(typeMvtStk);
        return MvtStkDto.fromEntity(
                repository.save(MvtStkDto.toEntity(dto))
        );
    }
}
