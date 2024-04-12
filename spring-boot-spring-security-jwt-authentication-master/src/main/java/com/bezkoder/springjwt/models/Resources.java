package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Resources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ResourceID")
    Long ResourceID;
    String Name;
    String Description;
    float Price ;
   // @Enumerated(EnumType.STRING)
   // ResourceStatus ReStatus ;
   // @Enumerated(EnumType.STRING)
   // ResourcesCategorie Categorie ;




   // @OneToMany(mappedBy = "resources")
   // private List<Stock> stocks;
    @JsonIgnore
    @ManyToMany(mappedBy ="resources",  cascade = CascadeType.ALL)
    List <Project> projects;



}
