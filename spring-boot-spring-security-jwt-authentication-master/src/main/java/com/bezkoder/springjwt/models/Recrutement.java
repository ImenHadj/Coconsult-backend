package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Recrutement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idRec;
    private String poste;
    private String lieu;
    private int postesVacants;
    private String image;
    private int  nbrdepostulants;
    //
    private String objectifs;
    private String Problematique;
    private String travail_demande;
    //
    private String experience;
    private String niveau_detude;
    private Double salaire;
    private String langue; // de mm
    private String sex;  // a elimine

    private LocalDate dateDebut;
    private LocalDate dateCloture;

    private String responsableRecrutement;

    @Enumerated(EnumType.STRING)
    private StatutRecrut statutRecrut;

    private String criteresSelection;
    private String mots_cles; //pr la recherche  expl

    @Enumerated(EnumType.STRING)
    private TypeRecrutement typeRecrutement;

    @OneToMany(mappedBy = "recrutementC")
    @JsonIgnore
    private List<Candidat> candidats;

    @ManyToMany
    List<RH> rh;

    @OneToMany (mappedBy = "recrutements")
    List<TestRecrutement>testRecrutements;

}

