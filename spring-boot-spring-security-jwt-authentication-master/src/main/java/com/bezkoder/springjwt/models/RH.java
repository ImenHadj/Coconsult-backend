package com.bezkoder.springjwt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


    @Entity
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public class RH implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idrh;
        private String  nom;
        private String prenom;

        @Enumerated(EnumType.STRING)
        private TypeIntitules TypeIntitules;

        @OneToMany(mappedBy = "responsableRH")
        private List<Evenement> evenementsOrganises;
        @ManyToMany
        List<Recrutement> recrutementRh;
    }


