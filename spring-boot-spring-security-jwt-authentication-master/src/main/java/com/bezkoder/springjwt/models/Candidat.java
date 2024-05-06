package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Candidat  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCandidat;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private String nationalite;
    private String genre;
    private String cvUrl;
    private String lettreMotivationUrl;
    private String portfolioUrl;
    private Integer score;
    private LocalDate Datepostule;
    @Enumerated(EnumType.STRING)
    private  niveauDetude niveauDetude;
    @Enumerated(EnumType.STRING)
    private  Experience experience;

    @Enumerated(EnumType.STRING)
    private  StatutCandidat StatutCandidat =  com.bezkoder.springjwt.models.StatutCandidat.EN_ATTENTE;

    @ManyToOne (cascade = CascadeType.ALL)
    @JsonIgnore
    private Recrutement recrutementC;

    @OneToOne(mappedBy = "candidat")
    private DetailRecrutement detailRecrutement;

    public Candidat(String cvUrl) {

        this.cvUrl = cvUrl;
        this.prenom = prenom;


    }





}
