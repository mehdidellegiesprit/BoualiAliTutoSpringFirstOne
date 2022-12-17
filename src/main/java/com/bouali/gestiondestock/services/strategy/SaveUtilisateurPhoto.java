package com.bouali.gestiondestock.services.strategy;

import com.bouali.gestiondestock.dto.FournisseurDto;
import com.bouali.gestiondestock.dto.UtilisateurDto;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidOperationException;
import com.bouali.gestiondestock.model.Utilisateur;
import com.bouali.gestiondestock.services.FlickrService;
import com.bouali.gestiondestock.services.FournisseurService;
import com.bouali.gestiondestock.services.UtilisateurService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("utilisateurStrategy")
@Slf4j
public class SaveUtilisateurPhoto implements Strategy<UtilisateurDto> {

    private FlickrService flickrService ;
    private UtilisateurService utilisateurService;

    @Autowired
    public SaveUtilisateurPhoto(FlickrService flickrService, UtilisateurService utilisateurService) {
        this.flickrService = flickrService;
        this.utilisateurService = utilisateurService;
    }

    @Override
    public UtilisateurDto savePhoto(Integer id,InputStream photo, String titre) throws FlickrException {
        UtilisateurDto utilisateur = utilisateurService.findById(id) ;
        // TODO FlickrService il va nous enregistrer nos photo sur le cloud
        String urlPhoto = flickrService.savePhoto(photo,titre);
        if (!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur l'ors de l'enregistrement de photo du l'utilisateur", ErrorCodes.UPDATE_PHOTO_EXCEPTION) ;
        }
        utilisateur.setPhoto(urlPhoto);

        return utilisateurService.save(utilisateur) ;
    }
}
