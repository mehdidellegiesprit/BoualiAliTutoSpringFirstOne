package com.bouali.gestiondestock.services.impl;

import com.bouali.gestiondestock.Repository.ClientRepository;
import com.bouali.gestiondestock.Repository.EntrepriseRepository;
import com.bouali.gestiondestock.dto.ClientDto;
import com.bouali.gestiondestock.dto.EntrepriseDto;
import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidEntityException;
import com.bouali.gestiondestock.model.Client;
import com.bouali.gestiondestock.model.Entreprise;
import com.bouali.gestiondestock.services.EntrepriseService;
import com.bouali.gestiondestock.validator.ClientValidator;
import com.bouali.gestiondestock.validator.EntrepriseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

    private EntrepriseRepository entrepriseRepository ;

    @Autowired //injection des dependace par constructeur
    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository){
        this.entrepriseRepository=entrepriseRepository;
    }

    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        List<String> errors = EntrepriseValidator.validate(dto) ;
        if(!errors.isEmpty()){
            log.error("Entreprise is not valid{} ",dto) ;
            throw new InvalidEntityException("L'entreprise n'est pas valide", ErrorCodes.ENTREPRISE_NOT_VALID,errors);
        }
        return EntrepriseDto.fromEntity(
                entrepriseRepository.save(EntrepriseDto.toEntity(dto))
        );
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        if(id==null){
            log.error("Entreprise ID is null") ;
            return null ;
        }
        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);
        return Optional.of(EntrepriseDto.fromEntity(entreprise.get())).orElseThrow(()->
                new EntityNotFoundException(
                        "Aucun Entreprise avec l'ID = "+ id + "n'ete trouve dans la BDD",
                        ErrorCodes.ENTREPRISE_NOT_FOUND
                )
        );
    }

    @Override
    public List<EntrepriseDto> findALl() {
        return entrepriseRepository.findAll()
                .stream()
                .map(EntrepriseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("Entreprise ID is null") ;
            return;
        }
        entrepriseRepository.deleteById(id);
    }
}
