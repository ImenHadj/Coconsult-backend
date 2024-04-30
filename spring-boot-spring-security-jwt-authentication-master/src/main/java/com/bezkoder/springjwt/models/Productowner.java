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
public class Productowner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idproductowner")
    private Long idproductowner; // Clé primaire
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    @JsonIgnore

    @OneToMany(mappedBy= "productowner", fetch = FetchType.EAGER)
    private List<Client> clients;


}
