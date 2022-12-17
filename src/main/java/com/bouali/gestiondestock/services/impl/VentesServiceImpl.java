package com.bouali.gestiondestock.services.impl;

import com.bouali.gestiondestock.Repository.ArticleRepository;
import com.bouali.gestiondestock.Repository.CommandeClientRepository;
import com.bouali.gestiondestock.Repository.LigneVenteRepository;
import com.bouali.gestiondestock.Repository.VentesRepository;
import com.bouali.gestiondestock.dto.*;
import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidEntityException;
import com.bouali.gestiondestock.exception.InvalidOperationException;
import com.bouali.gestiondestock.model.*;
import com.bouali.gestiondestock.services.MvtStkService;
import com.bouali.gestiondestock.services.VentesService;
import com.bouali.gestiondestock.validator.CommandeFournisseurValidator;
import com.bouali.gestiondestock.validator.VentesValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VentesServiceImpl implements VentesService {

    private ArticleRepository articleRepository ;
    private VentesRepository ventesRepository ;
    private LigneVenteRepository ligneVenteRepository ;
    private MvtStkService mvtStkService ;

    @Autowired
    public VentesServiceImpl(ArticleRepository articleRepository,
                             VentesRepository ventesRepository,
                             LigneVenteRepository ligneVenteRepository,
                             MvtStkService mvtStkService){
        this.articleRepository=articleRepository;
        this.ventesRepository=ventesRepository;
        this.ligneVenteRepository=ligneVenteRepository;
        this.mvtStkService=mvtStkService;
    }

    @Override
    public VentesDto save(VentesDto dto) {
        // 1ere niveau  de validation de la CommandeFournisseurDto
        List<String> errors = VentesValidator.validate(dto);

        if(!errors.isEmpty()){
            log.error("Ventes  is not valid{} ",dto) ;
            throw new InvalidEntityException("Vente  n'est pas valide", ErrorCodes.VENTE_NOT_VALID,errors);
        }

        List<String> articlesErrors = new ArrayList<>() ;
        dto.getLigneVentes().forEach(ligneVenteDto -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId()) ;
            if (article.isEmpty()){
                articlesErrors.add("Aucun article avec l'ID "+ligneVenteDto.getArticle().getId()+"n'a ete trouve dans la BDD") ;

            }
        });
        if(!articlesErrors.isEmpty()){
            log.error("Ventes  is not valid {} ",articlesErrors) ;
            throw new InvalidEntityException("Vente  n'est pas valide", ErrorCodes.VENTE_NOT_VALID,errors);
        }

        Ventes savedVente = ventesRepository.save(VentesDto.toEntity(dto)) ;

        if (dto.getLigneVentes()!=null){
            dto.getLigneVentes().forEach(ligVente->{
                LigneVente ligneVente = LigneVenteDto.toEntity(ligVente) ;
                ligneVente.setVente(savedVente);
                ligneVenteRepository.save(ligneVente);
                updateMvtStk(ligneVente);
            });
        }
        return  VentesDto.fromEntity(savedVente);

    }

    @Override
    public VentesDto findById(Integer id) {
        if(id==null){
            log.error("Vente ID is null") ;
            return null ;
        }
        return ventesRepository.findById(id)
                .map(VentesDto::fromEntity)
                .orElseThrow(()->new EntityNotFoundException("Aucun vente n'a ete trouve dans la BDD ",ErrorCodes.VENTE_NOT_FOUND)) ;
    }

    @Override
    public VentesDto findByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("Vente CODE is null") ;
            return null ;
        }

        return ventesRepository.findVentesByCode(code)
                .map(VentesDto::fromEntity)// map is used to convert entity to an dto the result of categoryRepository.findCategoryByCode(codeCategory)
                // is optional if it is exist it will be converted to an dto else  .orElseThrow will be treated
                .orElseThrow(()-> new EntityNotFoundException(
                                "Aucun Vente avec le CODE = "+ code + "n'ete trouve dans la BDD",
                                ErrorCodes.VENTE_NOT_FOUND
                        )
                );

    }

    @Override
    public List<VentesDto> findALl() {
        return ventesRepository.findAll()
                .stream()
                .map(VentesDto::fromEntity)
                .collect(Collectors.toList());

    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("Vente ID is null") ;
            return;
        }
        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByVenteId(id);
        if (!ligneVentes.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une vente  qui a  deja des ligne de vente  ",
                    ErrorCodes.VENTE_ALREADY_IN_USE) ;
        }
        ventesRepository.deleteById(id);
    }

    private void updateMvtStk (LigneVente lig){
        MvtStkDto mvtStkDto = MvtStkDto.builder()
                .article(ArticleDto.fromEntity(lig.getArticle()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.SORTIE)
                .sourceMvt(SourceMvtStk.VENTE)
                .quantite(lig.getQuantite())
                .idEntreprise(lig.getIdEntreprise())
                .build();
        mvtStkService.sortieStock(mvtStkDto);

    }
}
