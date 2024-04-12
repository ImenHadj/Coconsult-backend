package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.Project;
import com.bezkoder.springjwt.models.Task;
import org.springframework.http.ResponseEntity;

import java.util.List;

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
}
