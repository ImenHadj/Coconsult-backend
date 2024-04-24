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
public class Contract implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idContract")

    private Long idContract; // Clé primaire
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    private LocalDateTime contractDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    private LocalDateTime endDate;
    private int version; //track contract changes
    private String item_description;
    private BigDecimal payment_terms;
    private String contract_status;
    @Enumerated(EnumType.STRING)
    TypeContrat typeContrat;
    @JsonIgnore
    @ManyToOne
    Client client;

    @JsonIgnore
    @OneToMany(mappedBy= "contract" , cascade = CascadeType.ALL)
    private List<Facture> factures;
    @JsonIgnore
    @OneToMany(mappedBy= "contract", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<contratNotif> contratNotif;





}