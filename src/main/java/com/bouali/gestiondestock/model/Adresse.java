package com.bouali.gestiondestock.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true)
@Embeddable
public class Adresse implements Serializable {


    @Column(name="adresse1")
    private String adresse1 ;

    @Column(name="adresse2")
    private String adresse2;

    @Column(name="ville")
    private String ville ;


    @Column(name="codepostale")
    private String codePostale ;

    @Column(name="pays")
    private String pays ;




}
