package com.bouali.gestiondestock.Repository;

import com.bouali.gestiondestock.model.Article;
import com.bouali.gestiondestock.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Optional<Category> findCategoryByCode(String codeCategory);
}
