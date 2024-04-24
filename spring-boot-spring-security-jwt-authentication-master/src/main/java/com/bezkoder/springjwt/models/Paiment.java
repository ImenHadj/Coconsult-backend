package com.bezkoder.springjwt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Paiment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idpaiement ")
    private Long idpaiment; // Clé primaire
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    private Date payment_date ;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    Typepaiment typepaiment;


    @ManyToOne
    @JoinColumn(name = "idClient")
    Client client;
    @ManyToOne
    @JoinColumn(name = "idFacture")
    Facture facture;
}



