package com.bezkoder.springjwt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContratEmployee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_contrat_e;
    private String rib;
    private Long numeroSecuriteSociale;
    @Temporal(TemporalType.DATE)
    private LocalDate date_debut;
    @Temporal(TemporalType.DATE)
    private LocalDate date_fin;
    @Enumerated(EnumType.STRING)
    private ContratEmployeeType typeCE;
    private int duree_hebdomadaire;
    private Float salaire_base;
    private Boolean isArchive;
    @ManyToOne(cascade = CascadeType.ALL)
    Employee empl;
}
