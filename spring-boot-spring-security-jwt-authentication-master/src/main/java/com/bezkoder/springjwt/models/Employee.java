package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_employe ;
    private String username ;
    @Temporal(TemporalType.DATE)
    private Date date_embauche ;
   // @Enumerated(EnumType.STRING)
   // private PosteEmployee PosteEmployee;
    private Float salaire_base ;
    private String justification;
  /*  @ManyToOne(cascade = CascadeType.ALL)
    Departement departement;
    @OneToMany(mappedBy="employee",fetch = FetchType.EAGER)
    Set<Conge> conges;

    @OneToMany(mappedBy="employe",fetch = FetchType.EAGER)
    Set<SalaireEmployee> salaireEmployees;

    @OneToOne(cascade = CascadeType.ALL)
    PerformanceEmployee performanceEmployee;

    @OneToOne(cascade = CascadeType.ALL)
    HistoriqueEmployee historiqueEmployee;

    @OneToMany(mappedBy="empl",fetch = FetchType.EAGER)
    Set<ContratEmployee> contratEmployees;
    @OneToMany(mappedBy="emp",fetch = FetchType.EAGER)
    Set<Absence> absences;*/
    @JsonIgnore
    @ManyToOne (cascade = CascadeType.ALL)
    Team teams;

    //@ManyToOne(optional = true)
    // private User user;
}
