package com.bouali.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CategoryDto {
    private String code ;

    private String designation ;

    private List<ArticleDto> articles ;
}
