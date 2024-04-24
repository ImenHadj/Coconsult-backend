package com.bezkoder.springjwt.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
    private Float montant_heures_supplementaires ;
    private Float montant_Conge_Absence ;
    private Boolean isArchive;
    private int pourcentage;

    @ManyToOne(cascade = CascadeType.ALL)
    Employee empl;
}
