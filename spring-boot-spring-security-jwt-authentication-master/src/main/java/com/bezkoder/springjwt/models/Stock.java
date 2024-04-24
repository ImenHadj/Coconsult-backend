package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="StockID")
    Long stockID;
    int quantity;
    String location;
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

    @ManyToOne
    @JsonIgnore
    Fournisseur fournisseur;

    @OneToOne
    @JsonIgnore
    private Resources resource;

    @OneToOne(mappedBy="stockC")
    @JsonIgnore
    private Commande commande;


}
