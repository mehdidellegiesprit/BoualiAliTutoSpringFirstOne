package com.bouali.gestiondestock.Repository;

import com.bouali.gestiondestock.model.CommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeClientRepository extends JpaRepository<CommandeClient,Integer> {
    Optional<CommandeClient> findCommandeClientByCode (String code ) ;

    List<CommandeClient> findAllByClientId(Integer id ) ;
}
