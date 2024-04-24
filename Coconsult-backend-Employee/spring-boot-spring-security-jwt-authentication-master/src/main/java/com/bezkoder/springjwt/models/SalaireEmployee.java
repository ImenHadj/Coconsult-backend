package com.bezkoder.springjwt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SalaireEmployee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_salaire ;

    private Float salaire_base;
    private Float prime ;
    private Float heures_supplementaires ;
    private Float montant_heures_supplementaires ;
    private Float total_salaire;
    private Boolean isArchive ;
    @ManyToOne(cascade = CascadeType.ALL)
    Employee employe;

}
