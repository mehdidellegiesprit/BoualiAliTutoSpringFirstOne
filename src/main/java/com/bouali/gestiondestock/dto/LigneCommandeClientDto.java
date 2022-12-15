package com.bouali.gestiondestock.dto;


import com.bouali.gestiondestock.model.LigneCommandeClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LigneCommandeClientDto {

    private Integer id ;

    private ArticleDto article ;

    private CommandeClientDto commandeClient ;

    private BigDecimal quantite ;

    // le prix d'achat important dans l historique
    private BigDecimal prixUnitaire ;

    public static LigneCommandeClientDto fromEntity (LigneCommandeClient ligneCommandeClient){

        if (ligneCommandeClient==null){
            return null ;
        }
        return LigneCommandeClientDto.builder()
                .id(ligneCommandeClient.getId())
                .quantite(ligneCommandeClient.getQuantite())
                .prixUnitaire(ligneCommandeClient.getPrixUnitaire())
                .article(ArticleDto.fromEntity(ligneCommandeClient.getArticle()))
                .commandeClient(CommandeClientDto.fromEntity(ligneCommandeClient.getCommandeClient()))
                .build();
    }

    public static LigneCommandeClient toEntity (LigneCommandeClientDto ligneCommandeClientDto){
        if (ligneCommandeClientDto==null){
            return null ;
        }
        LigneCommandeClient ligneCommandeClient = new LigneCommandeClient() ;
        ligneCommandeClient.setId(ligneCommandeClientDto.getId());
        ligneCommandeClient.setQuantite(ligneCommandeClientDto.getQuantite());
        ligneCommandeClient.setPrixUnitaire(ligneCommandeClientDto.getPrixUnitaire());
        ligneCommandeClient.setArticle(ArticleDto.toEntity(ligneCommandeClientDto.getArticle()));
        ligneCommandeClient.setArticle(ArticleDto.toEntity(ligneCommandeClientDto.getArticle()));
        ligneCommandeClient.setCommandeClient(CommandeClientDto.toEntity(ligneCommandeClientDto.getCommandeClient()));
        return ligneCommandeClient;

    }
}
