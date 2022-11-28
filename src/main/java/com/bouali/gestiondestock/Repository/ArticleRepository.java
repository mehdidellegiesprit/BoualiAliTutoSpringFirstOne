package com.bouali.gestiondestock.Repository;

import com.bouali.gestiondestock.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository <Integer, Article>{
}
