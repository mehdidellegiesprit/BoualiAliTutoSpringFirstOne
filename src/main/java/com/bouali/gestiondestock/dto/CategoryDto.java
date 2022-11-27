package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CategoryDto {

    private Integer id ;

    private String code ;

    private String designation ;

    @JsonIgnore
    private List<ArticleDto> articles ;

    public static CategoryDto fromEntity(Category category){
        if (category==null){
            return null ;
            //TODO throw an exception
        }
        //Mapping de Category  => CategoryDto
        return CategoryDto.builder()
                .id(category.getId())
                .code(category.getCode())
                .designation(category.getDesignation())
                .build();
    }
    public static Category toEntity(CategoryDto categoryDto){
        if (categoryDto==null){
            return null ;
            //TODO throw an exception
        }
        //Mapping de  CategoryDto => Category

        Category category = new Category() ;
        category.setId(categoryDto.getId());
        category.setCode(categoryDto.getCode());
        category.setDesignation(categoryDto.getDesignation());
        return category;
    }
}
