package com.bouali.gestiondestock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChangerMotDePasseUtilisateurDto {
    private Integer id ;
    private String motDePasse ;
    private String confirmMotDePasse ;
}
