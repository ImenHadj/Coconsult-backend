package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DetailRecrutement  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetailRecrut;
    private LocalDate dateEntretien;
    private String evaluateur;
    private String commentairesEvaluateur;


    private LocalDateTime datePropositionOffre;
    private LocalDateTime dateReponseCandidat;

    @Enumerated(EnumType.STRING)
    private StatutEntretien statutEntretien = StatutEntretien.EN_ATTENTE;

    @Enumerated(EnumType.STRING)
    private ResultatOffre statutOffre;

    @OneToOne
    @JsonIgnore
    private Candidat candidat;

}
