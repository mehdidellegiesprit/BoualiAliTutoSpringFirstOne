package com.bouali.gestiondestock.Repository;

import com.bouali.gestiondestock.model.Article;
import com.bouali.gestiondestock.model.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VentesRepository extends JpaRepository<Ventes,Integer> {

    Optional<Ventes> findVentesByCode(String code) ;
}
