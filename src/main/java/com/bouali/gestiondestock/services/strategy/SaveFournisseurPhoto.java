package com.bouali.gestiondestock.services.strategy;

import com.bouali.gestiondestock.dto.EntrepriseDto;
import com.bouali.gestiondestock.dto.FournisseurDto;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidOperationException;
import com.bouali.gestiondestock.model.Fournisseur;
import com.bouali.gestiondestock.services.EntrepriseService;
import com.bouali.gestiondestock.services.FlickrService;
import com.bouali.gestiondestock.services.FournisseurService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;


@Service("fournisseurStrategy")
@Slf4j
public class SaveFournisseurPhoto implements Strategy<FournisseurDto> {

    private FlickrService flickrService ;
    private FournisseurService fournisseurService;

    @Autowired
    public SaveFournisseurPhoto(FlickrService flickrService, FournisseurService fournisseurService) {
        this.flickrService = flickrService;
        this.fournisseurService = fournisseurService;
    }

    @Override
    public FournisseurDto savePhoto(Integer id,InputStream photo, String titre) throws FlickrException {
        FournisseurDto fournisseur = fournisseurService.findById(id) ;
        // TODO FlickrService il va nous enregistrer nos photo sur le cloud
        String urlPhoto = flickrService.savePhoto(photo,titre);
        if (!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur l'ors de l'enregistrement de photo du fournisseur", ErrorCodes.UPDATE_PHOTO_EXCEPTION) ;
        }
        fournisseur.setPhoto(urlPhoto);

        return fournisseurService.save(fournisseur) ;
    }
}
