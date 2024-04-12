package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long team_id;
    private String team_name;
    private boolean availability;
//    @OneToMany(mappedBy = "team")
//    private List<Project> projects;
    @OneToMany(mappedBy ="teem",fetch = FetchType.EAGER )
    @JsonIgnore
    private List<Employee> employees;

}
