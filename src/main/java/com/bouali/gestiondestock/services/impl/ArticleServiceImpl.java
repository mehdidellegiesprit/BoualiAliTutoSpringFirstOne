package com.bouali.gestiondestock.services.impl;

import com.bouali.gestiondestock.Repository.ArticleRepository;
import com.bouali.gestiondestock.Repository.LigneCommandeClientRepository;
import com.bouali.gestiondestock.Repository.LigneCommandeFournisseurRepository;
import com.bouali.gestiondestock.Repository.LigneVenteRepository;
import com.bouali.gestiondestock.dto.ArticleDto;
import com.bouali.gestiondestock.dto.LigneCommandeClientDto;
import com.bouali.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.bouali.gestiondestock.dto.LigneVenteDto;
import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidEntityException;
import com.bouali.gestiondestock.model.Article;
import com.bouali.gestiondestock.model.LigneCommandeFournisseur;
import com.bouali.gestiondestock.services.ArticleService;
import com.bouali.gestiondestock.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository ;
    private LigneVenteRepository venteRepository ;
    private LigneCommandeFournisseurRepository commandeFournisseurRepository ;
    private LigneCommandeClientRepository commandeClientRepository ;



    @Autowired //injection des dependace par constructeur
    public ArticleServiceImpl(ArticleRepository articleRepository,
                              LigneVenteRepository venteRepository,
                              LigneCommandeFournisseurRepository commandeFournisseurRepository,
                              LigneCommandeClientRepository commandeClientRepository){
        this.articleRepository=articleRepository;
        this.venteRepository=venteRepository;
        this.commandeFournisseurRepository=commandeFournisseurRepository;
        this.commandeClientRepository=commandeClientRepository;
    }

    @Override
    public ArticleDto save(ArticleDto dto) {

        List<String> errors = ArticleValidator.validate(dto) ;
        if(!errors.isEmpty()){
            log.error("Article is not valid{} ",dto) ;
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID,errors);
        }
        return ArticleDto.fromEntity(
                articleRepository.save(ArticleDto.toEntity(dto))
        );
    }

    @Override
    public ArticleDto findById(Integer id) {
        if(id==null){
            log.error("Article ID is null") ;
            return null ;
        }
        Optional<Article> article = articleRepository.findById(id);
        return Optional.of(ArticleDto.fromEntity(article.get())).orElseThrow(()->
                new EntityNotFoundException(
                        "Aucun article avec l'ID = "+ id + "n'ete trouve dans la BDD",
                        ErrorCodes.ARTICLE_NOT_FOUND
                        )
        );
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        if(!StringUtils.hasLength(codeArticle)){
            log.error("Article CODE is null") ;
            return null ;
        }
        Optional<Article> article = articleRepository.findArticleByCodeArticle(codeArticle);

        return Optional.of(ArticleDto.fromEntity(article.get())).orElseThrow(()->
                new EntityNotFoundException(
                        "Aucun article avec le CODE = "+ codeArticle + "n'ete trouve dans la BDD",
                        ErrorCodes.ARTICLE_NOT_FOUND
                )
        );
    }

    @Override
    public List<ArticleDto> findALl() {
        return articleRepository.findAll()
                .stream() // boucler parcourir
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
        //TODO venteRepository ==== ligneventeRepository juste nommage  7abou houwa haka
        return venteRepository.findAllByArticleId(idArticle).stream()
                .map(LigneVenteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        //TODO commandeFournisseurRepository ==== LignecommandeFournisseurRepository juste nommage  7abou houwa haka
        return commandeClientRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle) {
        //TODO commandeFournisseurRepository ==== LignecommandeFournisseurRepository juste nommage  7abou houwa haka
        return commandeFournisseurRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findArticleByIdCategory(Integer idCategory) {
        return articleRepository.findAllByCategoryId(idCategory).stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("Article ID is null") ;
            return;
        }
        articleRepository.deleteById(id);

    }
}
