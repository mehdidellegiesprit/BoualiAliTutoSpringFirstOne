package com.bouali.gestiondestock.Repository;

import com.bouali.gestiondestock.model.Article;
import com.bouali.gestiondestock.model.LigneCommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneCommandeClientRepository extends JpaRepository<LigneCommandeClient,Integer> {
}
