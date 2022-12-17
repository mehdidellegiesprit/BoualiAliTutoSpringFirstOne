package com.bouali.gestiondestock.services.impl;

import com.bouali.gestiondestock.Repository.ArticleRepository;
import com.bouali.gestiondestock.Repository.CategoryRepository;
import com.bouali.gestiondestock.dto.CategoryDto;
import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidEntityException;
import com.bouali.gestiondestock.exception.InvalidOperationException;
import com.bouali.gestiondestock.model.Article;
import com.bouali.gestiondestock.model.Category;
import com.bouali.gestiondestock.services.CategoryService;
import com.bouali.gestiondestock.validator.ArticleValidator;
import com.bouali.gestiondestock.validator.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository ;
    private ArticleRepository articleRepository ;

    @Autowired //injection des dependace par constructeur
    public CategoryServiceImpl(CategoryRepository categoryRepository,ArticleRepository articleRepository){
        this.categoryRepository=categoryRepository;
        this.articleRepository=articleRepository;
    }

    @Override
    public CategoryDto save(CategoryDto dto) {
        List<String> errors = CategoryValidator.validate(dto) ;
        if(!errors.isEmpty()){
            log.error("Category is not valid{} ",dto) ;
            throw new InvalidEntityException("La category n'est pas valide", ErrorCodes.CATEGORY_NOT_VALID,errors);
        }
        return CategoryDto.fromEntity(
                categoryRepository.save(CategoryDto.toEntity(dto))
        );
    }

    @Override
    public CategoryDto findById(Integer id) {
        if(id==null){
            log.error("Catgory ID is null") ;
            return null ;
        }

        return categoryRepository.findById(id)
                .map(CategoryDto::fromEntity)
                .orElseThrow(()->new EntityNotFoundException(
                        "Aucun Category avec l'ID = "+ id + "n'ete trouve dans la BDD",
                        ErrorCodes.CATEGORY_NOT_FOUND
                )
        );
    }

    @Override
    public CategoryDto findByCode(String codeCategory) {
        if(!StringUtils.hasLength(codeCategory)){
            log.error("Category CODE is null") ;
            return null ;
        }

        return categoryRepository.findCategoryByCode(codeCategory)
                                 .map(CategoryDto::fromEntity)// map is used to convert entity to an dto the result of categoryRepository.findCategoryByCode(codeCategory)
                                                            // is optional if it is exist it will be converted to an dto else  .orElseThrow will be treated
                                 .orElseThrow(()-> new EntityNotFoundException(
                                    "Aucun category avec le CODE = "+ codeCategory + "n'ete trouve dans la BDD",
                                    ErrorCodes.CATEGORY_NOT_FOUND
                )
        );
    }


    @Override
    public List<CategoryDto> findALl() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("Article ID is null") ;
            return;
        }
        List<Article> articles = articleRepository.findAllByCategoryId(id);
        if (!articles.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer cette categorie deja utiliser  ",
                    ErrorCodes.CATEGORY_ALREADY_IN_USE) ;
        }
        categoryRepository.deleteById(id);
    }
}
