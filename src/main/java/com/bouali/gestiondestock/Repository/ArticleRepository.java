package com.bouali.gestiondestock.Repository;

import com.bouali.gestiondestock.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository <Article,Integer>{
    Optional<Article> findArticleByCodeArticle(String codeArticle);
}
