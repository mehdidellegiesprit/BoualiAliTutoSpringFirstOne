package com.bouali.gestiondestock.Repository;

import com.bouali.gestiondestock.model.Article;
import com.bouali.gestiondestock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Integer, Utilisateur> {
}
