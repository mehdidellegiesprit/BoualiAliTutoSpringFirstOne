package com.bouali.gestiondestock.dto;


import com.bouali.gestiondestock.model.Client;
import com.bouali.gestiondestock.model.Fournisseur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;


import java.util.List;

@Builder
@Data
public class FournisseurDto {

    private Integer id ;

    private String nom ;

    private String prenom ;


    // fix me plz dall
    @JsonIgnore
    private AdresseDto adresse ;

    private String photo ;

    private String mail ;

    private String numTel ;


    // fix me plz dall
    @JsonIgnore
    private List<CommandeFournisseurDto> commandeFournisseurs ;

    public static FournisseurDto fromEntity(Fournisseur fournisseur){
        if (fournisseur==null){
            return null ;
            //TODO throw an exception
        }
        //Mapping de Category  => CategoryDto
        return FournisseurDto.builder()
                .id(fournisseur.getId())
                .nom(fournisseur.getNom())
                .prenom(fournisseur.getPrenom())
                .photo(fournisseur.getPhoto())
                .mail(fournisseur.getMail())
                .numTel(fournisseur.getMail())
                .build();
    }
    public static Fournisseur toEntity(FournisseurDto fournisseurDto){
        if (fournisseurDto==null){
            return null ;
            //TODO throw an exception
        }
        //Mapping de  CategoryDto => Category

        Fournisseur fournisseur = new Fournisseur() ;
        fournisseur.setId(fournisseurDto.getId());
        fournisseur.setNom(fournisseurDto.getNom());
        fournisseur.setPrenom(fournisseurDto.getPrenom());
        fournisseur.setPhoto(fournisseurDto.getPhoto());
        fournisseur.setMail(fournisseurDto.getMail());
        fournisseur.setNumTel(fournisseurDto.getNumTel());
        return fournisseur;
    }
}
