package com.bezkoder.springjwt.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FournisseurID")
    Long fournisseurID;
    String nom;
    String address;
    String contact ;
    @Enumerated(EnumType.STRING)
    ResourcesCategorie typeFournisseur ;
    float score ;

    @OneToMany(mappedBy = "fournisseur")
    List<Stock> stocks;

}
