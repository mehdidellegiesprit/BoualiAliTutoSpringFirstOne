package com.bouali.gestiondestock.Repository;

import com.bouali.gestiondestock.model.Article;
import com.bouali.gestiondestock.model.LigneCommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface LigneCommandeClientRepository extends JpaRepository<LigneCommandeClient,Integer> {
    //List<LigneCommandeClient> findAllByCommandeClientId(Integer id);
    //@Query(value="select l.id from lignecommandeclient l",nativeQuery = true)
    List<LigneCommandeClient> findAllLigneCommandeClientByCommandeClientId(Integer id);
}
