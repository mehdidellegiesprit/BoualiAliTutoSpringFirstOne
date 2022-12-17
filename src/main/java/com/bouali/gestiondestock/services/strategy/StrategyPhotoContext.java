package com.bouali.gestiondestock.services.strategy;

import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidOperationException;
import com.flickr4java.flickr.FlickrException;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class StrategyPhotoContext {

    private BeanFactory beanFactory ;
    private Strategy strategy ;

    @Setter
    private String context ;

    @Autowired
    public StrategyPhotoContext(BeanFactory beanFactory,Strategy strategy) {
        this.beanFactory = beanFactory;
        this.strategy = strategy;
    }

    public Object savePhoto (String context , Integer id, InputStream photo , String title) throws FlickrException {
        determinContext(context) ;
        return strategy.savePhoto(id,photo,title);
    }

    private void determinContext(String context) {
        final String beanName = context+"Strategy" ;
        switch (context){
            case "article":
                strategy = beanFactory.getBean(beanName,SaveArticlePhoto.class);
                break ;
            case "client":
                strategy = beanFactory.getBean(beanName,SaveClientPhoto.class);
                break ;
            case "fournisseur":
                strategy = beanFactory.getBean(beanName,SaveFournisseurPhoto.class);
                break ;
            case "entreprise":
                strategy = beanFactory.getBean(beanName,SaveEntreprisePhoto.class);
                break ;
            case "utilisateur":
                strategy = beanFactory.getBean(beanName,SaveUtilisateurPhoto.class);
                break ;
            default:throw new InvalidOperationException("Context inconnu pour l'enregistrement de la photo", ErrorCodes.UNKNOWN_CONTEXT);
        }
    }
}
