package com.bouali.gestiondestock.controller;

import com.bouali.gestiondestock.controller.api.ArticleApi;
import com.bouali.gestiondestock.dto.ArticleDto;
import com.bouali.gestiondestock.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
public class ArticleController implements ArticleApi {

    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService){
        this.articleService=articleService;
    }

    @Override
    public ArticleDto save(ArticleDto dto) {
        return articleService.save(dto);
    }

    @Override
    public ArticleDto findById(Integer id) {
        return articleService.findById(id);
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        return articleService.findByCodeArticle(codeArticle);
    }

    @Override
    public List<ArticleDto> findALl() {
        return articleService.findALl();
    }

    @Override
    public void delete(Integer id) {
        articleService.delete(id);
    }
}
