package com.bouali.gestiondestock.services.impl;

import com.bouali.gestiondestock.Repository.CommandeClientRepository;
import com.bouali.gestiondestock.Repository.CommandeFournisseurRepository;
import com.bouali.gestiondestock.Repository.EntrepriseRepository;
import com.bouali.gestiondestock.Repository.FournisseurRepository;
import com.bouali.gestiondestock.dto.EntrepriseDto;
import com.bouali.gestiondestock.dto.FournisseurDto;
import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidEntityException;
import com.bouali.gestiondestock.exception.InvalidOperationException;
import com.bouali.gestiondestock.model.CommandeClient;
import com.bouali.gestiondestock.model.CommandeFournisseur;
import com.bouali.gestiondestock.model.Entreprise;
import com.bouali.gestiondestock.model.Fournisseur;
import com.bouali.gestiondestock.services.FournisseurService;
import com.bouali.gestiondestock.validator.EntrepriseValidator;
import com.bouali.gestiondestock.validator.FournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {


    private FournisseurRepository fournisseurRepository ;
    private CommandeFournisseurRepository commandeFournisseurRepository ;

    @Autowired //injection des dependace par constructeur
    public FournisseurServiceImpl(FournisseurRepository fournisseurRepository,CommandeFournisseurRepository commandeFournisseurRepository){
        this.fournisseurRepository=fournisseurRepository;
        this.commandeFournisseurRepository=commandeFournisseurRepository;
    }


    @Override
    public FournisseurDto save(FournisseurDto dto) {
        List<String> errors = FournisseurValidator.validate(dto) ;
        if(!errors.isEmpty()){
            log.error("Fournisseur is not valid ",dto) ;
            throw new InvalidEntityException("Fournisseur n'est pas valide", ErrorCodes.FOURNISSEUR_NOT_VALID,errors);
        }
        return FournisseurDto.fromEntity(
                fournisseurRepository.save(FournisseurDto.toEntity(dto))
        );
    }

    @Override
    public FournisseurDto findById(Integer id) {
        if(id==null){
            log.error("Fournisseur ID is null") ;
            return null ;
        }
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(id);
        return Optional.of(FournisseurDto.fromEntity(fournisseur.get())).orElseThrow(()->
                new EntityNotFoundException(
                        "Aucun Fournisseur avec l'ID = "+ id + "n'ete trouve dans la BDD",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND
                )
        );
    }

    @Override
    public List<FournisseurDto> findALl() {
        return fournisseurRepository.findAll()
                .stream()
                .map(FournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("Fournisseur ID is null") ;
            return;
        }
        List<CommandeFournisseur> commandeFournisseurs = commandeFournisseurRepository.findAllByFournisseurId(id) ;
        if (!commandeFournisseurs.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un fournisseur  qui a  deja des commandes  ",
                    ErrorCodes.FOURNISSEUR_ALREADY_IN_USE) ;
        }
        fournisseurRepository.deleteById(id);
    }
}
