package com.bouali.gestiondestock.services.strategy;

import com.bouali.gestiondestock.dto.ClientDto;
import com.bouali.gestiondestock.dto.EntrepriseDto;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidOperationException;
import com.bouali.gestiondestock.model.Entreprise;
import com.bouali.gestiondestock.services.ClientService;
import com.bouali.gestiondestock.services.EntrepriseService;
import com.bouali.gestiondestock.services.FlickrService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("entrepriseStrategy")
@Slf4j
public class SaveEntreprisePhoto implements Strategy<EntrepriseDto> {

    private FlickrService flickrService ;
    private EntrepriseService entrepriseService;

    @Autowired
    public SaveEntreprisePhoto(FlickrService flickrService, EntrepriseService entrepriseService) {
        this.flickrService = flickrService;
        this.entrepriseService = entrepriseService;
    }

    @Override
    public EntrepriseDto savePhoto(Integer id,InputStream photo, String titre) throws FlickrException {
        EntrepriseDto entreprise = entrepriseService.findById(id) ;
        // TODO FlickrService il va nous enregistrer nos photo sur le cloud
        String urlPhoto = flickrService.savePhoto(photo,titre);
        if (!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur l'ors de l'enregistrement de photo de l'entreprise", ErrorCodes.UPDATE_PHOTO_EXCEPTION) ;
        }
        entreprise.setPhoto(urlPhoto);

        return entrepriseService.save(entreprise) ;
    }
}
