package com.bezkoder.springjwt.models;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnore;
=======
>>>>>>> Rh
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectid;
    private String projectname;
    private String projectdescription;
    private Date startDate;
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
    @Enumerated(EnumType.STRING)
    private ProjectType type;
    private double budget;
    private double cost;
    private double expectedRevenue;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @JsonIgnore
    @OneToOne
    private Team team;
    @JsonIgnore
    @OneToMany(mappedBy ="project",  cascade = CascadeType.ALL)
    private List<Task> tasks;
    @JsonIgnore
    @ManyToMany
    List<Consultant>  consultants;
    @JsonIgnore
    @ManyToMany
    List<Resources> resources;
}
