package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_employe ;
    @Temporal(TemporalType.DATE)
    private Date date_embauche ;
    @Enumerated(EnumType.STRING)
    private PosteEmployee PosteEmployee;
    @ManyToOne(cascade = CascadeType.ALL)
    Departement departement;
    @OneToMany(mappedBy="employee",fetch = FetchType.EAGER)
    @JsonIgnore
    Set<Conge> conges;

    @OneToMany(mappedBy="employe",fetch = FetchType.EAGER)
    @JsonIgnore
    Set<SalaireEmployee> salaireEmployees;

    @OneToMany(mappedBy="employeee",fetch = FetchType.EAGER)
    Set<Note> notes;

    @OneToMany(mappedBy="empl",fetch = FetchType.EAGER)
    @JsonIgnore
    Set<ContratEmployee> contratEmployees;
    @OneToMany(mappedBy="emp",fetch = FetchType.EAGER)
    @JsonIgnore
    Set<Absence> absences;
    private int nbrJourConge = 26;

    @ManyToOne (cascade = CascadeType.ALL)
    Team teem;
    @OneToOne(cascade = CascadeType.ALL)
    PerformanceEmployee performanceEmployee;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
