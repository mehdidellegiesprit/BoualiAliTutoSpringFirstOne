package com.bouali.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.time.Instant;

@Builder
@Data
public class VentesDto {
    private String code ;

    private Instant dateVente ;

    private String commentaire ;
}
