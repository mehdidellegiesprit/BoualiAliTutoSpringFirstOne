package com.bouali.gestiondestock.services.impl;

import com.bouali.gestiondestock.Repository.RolesRepository;
import com.bouali.gestiondestock.Repository.UtilisateurRepository;
import com.bouali.gestiondestock.dto.RolesDto;
import com.bouali.gestiondestock.dto.UtilisateurDto;
import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.model.Roles;
import com.bouali.gestiondestock.model.Utilisateur;
import com.bouali.gestiondestock.services.RolesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RolesServiceImpl implements RolesService {

    private RolesRepository rolesRepository ;
    private UtilisateurRepository utilisateurRepository ;

    @Autowired //injection des dependace par constructeur
    public RolesServiceImpl(RolesRepository rolesRepository,UtilisateurRepository utilisateurRepository ){
        this.rolesRepository=rolesRepository;
        this.utilisateurRepository=utilisateurRepository;
    }

    @Override
    public RolesDto save(RolesDto dto) {
        return RolesDto.fromEntity(
                rolesRepository.save(RolesDto.toEntity(dto)));
    }

    @Override
    public RolesDto findById(Integer id) {
        if(id==null){
            log.error("Role ID fournit is null") ;
            return null ;
        }
        Optional<Roles> role = rolesRepository.findById(id);
        return Optional.of(RolesDto.fromEntity(role.get())).orElseThrow(()->
                new EntityNotFoundException(
                        "Aucun Role avec l'ID = "+ id + "n'ete trouve dans la BDD",
                        ErrorCodes.ROLE_NOT_FOUND
                )
        );
    }
    //List<Roles>
    @Override
    public List<RolesDto> findRolesByUtilisateur(Integer idUtilisateur) {
        return rolesRepository.findRolesByUtilisateur(idUtilisateur)
                .stream()
                .map(RolesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RolesDto> findALl() {
        return rolesRepository.findAll()
                .stream()
                .map(RolesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("Role ID is null") ;
            return;
        }
        rolesRepository.deleteById(id);
    }
}
