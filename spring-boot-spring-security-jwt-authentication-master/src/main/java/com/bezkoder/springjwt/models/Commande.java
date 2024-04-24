package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="commandeID")
    Long commandeID;
    int quantity;
    String location;
    Long fournissID;
    Boolean replenishmentAlert ;
    @Temporal(TemporalType.DATE)
    Date entryDate ;
    @Temporal(TemporalType.DATE)
    Date purchaseDate ;
    @Temporal(TemporalType.DATE)
    Date expirationDate ;
    String quality;
    int pourcentageDefauts ;
    @Enumerated(EnumType.STRING)
    ResourcesCategorie categorieStock ;
    @Enumerated(EnumType.STRING)
    StatusCommande statusCommande ;



    @OneToOne
    @JsonIgnore
    private Stock stockC;
}
