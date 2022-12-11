package com.bouali.gestiondestock.services.impl;

import com.bouali.gestiondestock.Repository.FournisseurRepository;
import com.bouali.gestiondestock.Repository.UtilisateurRepository;
import com.bouali.gestiondestock.dto.FournisseurDto;
import com.bouali.gestiondestock.dto.UtilisateurDto;
import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidEntityException;
import com.bouali.gestiondestock.model.Fournisseur;
import com.bouali.gestiondestock.model.Utilisateur;
import com.bouali.gestiondestock.services.UtilisateurService;
import com.bouali.gestiondestock.validator.FournisseurValidator;
import com.bouali.gestiondestock.validator.UtilisateurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {


    private UtilisateurRepository utilisateurRepository ;

    @Autowired //injection des dependace par constructeur
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository){
        this.utilisateurRepository=utilisateurRepository;
    }


    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        List<String> errors = UtilisateurValidator.validate(dto) ;
        if(!errors.isEmpty()){
            log.error("Utilisateur is not valid ",dto) ;
            throw new InvalidEntityException("Utilisateur n'est pas valide", ErrorCodes.UTILISATEUR_NOT_VALID,errors);
        }
        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(UtilisateurDto.toEntity(dto))
        );
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        if(id==null){
            log.error("Utilisateur ID is null") ;
            return null ;
        }
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        return Optional.of(UtilisateurDto.fromEntity(utilisateur.get())).orElseThrow(()->
                new EntityNotFoundException(
                        "Aucun Utilisateur avec l'ID = "+ id + "n'ete trouve dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND
                )
        );
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        return utilisateurRepository.findUtilisateurByEmail(email)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(()->new EntityNotFoundException(
                        "Aucun Utilisateur avec l'EMAIL = "+ email + "n'ete trouve dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND
                ));
    }

    @Override
    public List<UtilisateurDto> findALl() {
        return utilisateurRepository.findAll()
                .stream()
                .map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("Utilisateur ID is null") ;
            return;
        }
        utilisateurRepository.deleteById(id);
    }
}
