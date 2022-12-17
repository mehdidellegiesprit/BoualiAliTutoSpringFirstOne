package com.bouali.gestiondestock.services.strategy;

import com.bouali.gestiondestock.dto.ArticleDto;
import com.bouali.gestiondestock.dto.ClientDto;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidOperationException;
import com.bouali.gestiondestock.model.Client;
import com.bouali.gestiondestock.services.ArticleService;
import com.bouali.gestiondestock.services.ClientService;
import com.bouali.gestiondestock.services.FlickrService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("clientStrategy")
@Slf4j
public class SaveClientPhoto implements Strategy<ClientDto> {

    // TODO FlickrService il va nous enregistrer nos photo sur le cloud
    private FlickrService flickrService ;
    private ClientService clientService;

    @Autowired
    public SaveClientPhoto(FlickrService flickrService, ClientService clientService) {
        this.flickrService = flickrService;
        this.clientService = clientService;
    }


    @Override
    public ClientDto savePhoto(Integer id,InputStream photo, String titre) throws FlickrException {
        ClientDto client = clientService.findById(id) ;
        // TODO FlickrService il va nous enregistrer nos photo sur le cloud
        String urlPhoto = flickrService.savePhoto(photo,titre);
        if (!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur l'ors de l'enregistrement de photo du client", ErrorCodes.UPDATE_PHOTO_EXCEPTION) ;
        }
        client.setPhoto(urlPhoto);

        return clientService.save(client) ;
    }
}
