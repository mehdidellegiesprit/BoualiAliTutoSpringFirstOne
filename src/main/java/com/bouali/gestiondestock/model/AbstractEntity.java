package com.bouali.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;


@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id ;

    @CreatedDate
    @Column(name="creationDate",nullable = false,updatable = false)
    private Instant creationDate ;

    @LastModifiedDate
    @Column(name="lastModifiedDate")
    private Instant lastModifiedDate;

    //prePersist or preInsert the same things avant a persistange ! cest avant l'enregistremrnt dans la BD

/*
    c'est un fix rapide
    @PrePersist
    void prePersist (){
        creationDate = Instant.now();
    }
    @PreUpdate
    void preUpdate (){
        lastModifiedDate = Instant.now();
    }*/


}
