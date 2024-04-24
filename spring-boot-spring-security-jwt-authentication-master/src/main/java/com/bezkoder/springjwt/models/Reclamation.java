package com.bezkoder.springjwt.models;

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
public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ReclamationID")
    Long reclamationID;
    Long employeID ;
    @Temporal(TemporalType.DATE)
    Date reclamationDate ;
    String categorie ;
    String departement;
    String description;
    @Enumerated(EnumType.STRING)
    Priority priorité ;
    @Enumerated(EnumType.STRING)
    ReclamationStatus statusReclamation ;

    @ManyToOne
    private User user;


}
