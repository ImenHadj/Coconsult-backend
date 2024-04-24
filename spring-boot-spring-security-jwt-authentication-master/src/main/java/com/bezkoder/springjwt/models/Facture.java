package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Facture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idFacture")
    private Long idFacture; // Clé primaire
    private String designation;
    private String currency;
    private Long invoice_number;//num facture
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    private LocalDateTime facture_date;
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    private LocalDateTime due_date;
    private BigDecimal total_amount;
    private BigDecimal paid_amount;
    private String  payment_status; // "Outstanding", "Partially paid", "Paid"
    private String notes;
    private String milestone_description;

    @ManyToOne
    Client client;

    @ManyToOne
    Contract contract;
    @JsonIgnore
    @OneToMany(mappedBy = "facture", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Paiment> paiments;

}
