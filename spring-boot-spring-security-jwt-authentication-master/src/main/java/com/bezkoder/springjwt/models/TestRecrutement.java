package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TestRecrutement  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTest;
    private String titre;
    private String description;

    @Enumerated(EnumType.STRING)
    private TypeTest typeTest;
    @Enumerated(EnumType.STRING)
    private ResultatRecrutement resultatRecrutement;

    private Integer duree;
    private String logicielUtilise;
    private String competencesRequises;

    @ManyToOne (cascade = CascadeType.ALL)
    @JsonIgnore
    Recrutement recrutements;

}