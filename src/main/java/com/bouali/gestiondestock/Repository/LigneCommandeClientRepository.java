package com.bouali.gestiondestock.Repository;

import com.bouali.gestiondestock.model.Article;
import com.bouali.gestiondestock.model.LigneCommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface LigneCommandeClientRepository extends JpaRepository<LigneCommandeClient,Integer> {
    List<LigneCommandeClient> findAllByCommandeClientId(Integer id);
}
