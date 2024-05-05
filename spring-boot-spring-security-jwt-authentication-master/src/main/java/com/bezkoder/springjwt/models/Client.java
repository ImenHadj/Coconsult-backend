package com.bezkoder.springjwt.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idClient")
    private Long idClient; // Clé primaire
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    private String companyAddress;
    private BigDecimal amount;
    @JsonIgnore

    @OneToMany(mappedBy= "client", fetch = FetchType.EAGER)
    private List<Facture> factures;

    @JsonIgnore
    @OneToMany(mappedBy= "client", fetch = FetchType.EAGER)
    private List<Paiment> paiments;

    @OneToMany(mappedBy= "client", fetch = FetchType.EAGER)
    private List<Contract> contracts;

    @JsonIgnore
    @OneToMany(mappedBy= "client", fetch = FetchType.EAGER)
    private List<Project> projects;

@JsonIgnore
@ManyToOne
Productowner productowner;

}
