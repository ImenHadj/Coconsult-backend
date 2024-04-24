package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskid;
    private String taskname;
    private String taskdescription;
    private String owner;
    private Date startDate;
    private Date endDate;
    private String duration;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private int progression;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private Date dueDate;
    @Enumerated(EnumType.STRING)
    private TypeDependance typeDependance;
    @JsonIgnore
    @ManyToOne
    Project project;

}
