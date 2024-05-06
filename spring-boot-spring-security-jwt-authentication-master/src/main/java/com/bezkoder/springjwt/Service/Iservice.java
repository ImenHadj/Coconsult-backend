package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface Iservice {
    Project addProject(Project p);
    Project updateProject(Long projectId, Project updatedProject);
    void removeProject(Long project_id);
    List<Project> getAllProjects();
    ResponseEntity<Project> getProjectById(Long id);
    List<Task> getAllTasks ();
    Task addTaskAndAssignToProject(Long projectId, Task task);
    //Consultant addConsultantAndAssignToProject(Long projectId, Consultant consultant);
    Task updateTask(Long taskId, Task updatedTask);
    ResponseEntity<Task> getTaskById(Long id);
    void deleteTask(Long taskId);
    void removeTask(Long taskId);
    void AssignTaskToEmployee(Long id_employe, Long taskId);
    List<Task> getAllTasksByEmployee(String username);
    List<Task> getTasksByProject(Long projectId) ;
    double calculCostProject (Long projectId);
    double calculateAverageProfitability();
    ResponseEntity<?> calculateStatisticsByType();
    List<Object[]> calculateProfitabilityForEachProject();
    ResponseEntity<?> getBestProjectOfTheYear();
    // Consultant addAndAssignConsultantToProjects(Consultant consultant, List<Long> projectIds);
    // void assignConsultantsToProject(Long projectId, List<Long> consultantIds);
    // Consultant addConsultant(Consultant C);
    // void deleteConsultant(Long id);
    // Consultant updateConsultant(Long id, Consultant updatedConsultant);
    // List<Consultant> getAllConsultants();
    // List<Consultant> getConsultantsByProject(Long projectId);
    // ResponseEntity<Consultant> getConsultantById(Long id);

    List<Object[]> calculateProfitabilityByYear();
    Team addTeam(Team team);
    void assignEmployeesToTeam(Set<Employee> employees, Long teamId);
    Team addTeamAndAssignToProject(Team team, Long projectId,Long id);
    double calculateProjectProgression(Long projectId);
    List<Team> getAllTeams();
    Team updateTeam(Long team_id, Team updatedTeam);
    void removeTeam(Long team_id);
    ResponseEntity<Team> getTeamById(Long id);
    // Consultant assignProjectsToConsultant(Long consultantId, List<Long> projectIds);
    Consultant addConsultantAndAssignToProject(Long projectId, Consultant consultant);

    List<User> getproductowners();
}
