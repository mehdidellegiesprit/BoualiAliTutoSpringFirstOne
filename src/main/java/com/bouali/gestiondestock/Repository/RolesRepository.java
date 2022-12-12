package com.bouali.gestiondestock.Repository;

import com.bouali.gestiondestock.model.Roles;
import com.bouali.gestiondestock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles,Integer> {
    @Query(value="select * from roles r  where r.idutilisateur = :idUtilisateur",nativeQuery = true)
    List<Roles> findRolesByUtilisateur(@Param("idUtilisateur") Integer idUtilisateur);





}
