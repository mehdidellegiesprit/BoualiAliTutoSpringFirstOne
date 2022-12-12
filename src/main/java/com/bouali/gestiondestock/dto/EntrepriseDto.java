package com.bouali.gestiondestock.dto;


import com.bouali.gestiondestock.model.CommandeFournisseur;
import com.bouali.gestiondestock.model.Entreprise;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EntrepriseDto {

    private Integer id ;

    private String nom ;

    private String description ;

    // fix me plz dall
    //@JsonIgnore
    private AdresseDto adresse ;

    private String codeFiscal ;

    private String photo ;

    private String email ;

    private String numTel ;

    private String steWeb ;

    // fix me plz dall
    //@JsonIgnore
    private List<UtilisateurDto> utilisateurs ;


    public static EntrepriseDto fromEntity (Entreprise entreprise){

        if (entreprise==null){
            return null ;
        }
        return EntrepriseDto.builder()
                .id(entreprise.getId())
                .nom(entreprise.getNom())
                .description(entreprise.getDescription())
                .codeFiscal(entreprise.getCodeFiscal())
                .photo(entreprise.getPhoto())
                .email(entreprise.getEmail())
                .numTel(entreprise.getNumTel())
                .steWeb(entreprise.getSteWeb())
                .adresse(AdresseDto.fromEntity(entreprise.getAdresse()))
                .build();
    }

    public static Entreprise toEntity (EntrepriseDto entrepriseDto){
        if (entrepriseDto==null){
            return null ;
        }
        Entreprise entreprise = new Entreprise() ;
        entreprise.setId(entrepriseDto.getId());
        entreprise.setNom(entrepriseDto.getNom());
        entreprise.setDescription(entrepriseDto.getDescription());
        entreprise.setCodeFiscal(entrepriseDto.getCodeFiscal());
        entreprise.setPhoto(entrepriseDto.getPhoto());
        entreprise.setEmail(entrepriseDto.getEmail());
        entreprise.setNumTel(entrepriseDto.getNumTel());
        entreprise.setSteWeb(entrepriseDto.getSteWeb());
        entreprise.setAdresse(AdresseDto.toEntity(entrepriseDto.getAdresse()));
        return entreprise;

    }
}
