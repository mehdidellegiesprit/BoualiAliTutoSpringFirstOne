package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.Client;
import com.bouali.gestiondestock.model.LigneCommandeClient;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.List;

@Builder
@Data
public class CommandeClientDto {
    private String code ;

    private Instant dateCommande ;

    private ClientDto client ;

    private List<LigneCommandeClientDto> ligneCommandeClients ;
}
