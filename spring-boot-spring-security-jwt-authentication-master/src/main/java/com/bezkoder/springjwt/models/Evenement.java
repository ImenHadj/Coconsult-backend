package com.bezkoder.springjwt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Evenement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvent;
    private String libelle;
    private String description;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    private String lieu;
    @Enumerated(EnumType.STRING)
    private TypeEvenement typeEvenement;
    private Integer nombreParticipants;
    private  String Certif;;

    @ManyToOne (cascade = CascadeType.ALL)
    private RH responsableRH;

}