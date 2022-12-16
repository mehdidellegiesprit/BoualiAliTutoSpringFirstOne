package com.bouali.gestiondestock.Repository;

import com.bouali.gestiondestock.model.Article;
import com.bouali.gestiondestock.model.LigneCommandeClient;
import com.bouali.gestiondestock.model.LigneCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface LigneCommandeFournisseurRepository extends JpaRepository<LigneCommandeFournisseur,Integer> {
    List<LigneCommandeFournisseur> findALlLigneCommandesFournisseurByCommandeFournisseurId(Integer idCommande);
    List<LigneCommandeFournisseur> findAllByArticleId(Integer idArticle);


}
