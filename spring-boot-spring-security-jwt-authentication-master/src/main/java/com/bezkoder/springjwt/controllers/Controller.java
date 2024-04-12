package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.Iservice;
import com.bezkoder.springjwt.models.Project;
import com.bezkoder.springjwt.models.Task;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins ="http://localhost:4200")

//@RequestMapping("/coconsult")
public class Controller {
    Iservice iservice;

    @PostMapping("/addProject")
    @ResponseBody
    public Project addProject(@RequestBody Project p) {
        Project project = iservice.addProject(p);
        return project;
    }

    @PutMapping("/updateproject/{idProject}")
    @ResponseBody
    public Project updateProject(@PathVariable("idProject") Long idProject, @RequestBody Project p) {
        Project project = iservice.updateProject(idProject, p);
        return project;
    }

    @DeleteMapping("/removeProject/{idProject}")
    @ResponseBody
    public void removeProject(@PathVariable("idProject") Long project_id) {
        iservice.removeProject(project_id);
    }


    @GetMapping("/getAll")
    @ResponseBody
    public List<Project> getAllProjects() {
        List<Project> projectList = iservice.getAllProjects();
        return projectList;
    }

    @GetMapping("/getAllT")
    @ResponseBody
    public List<Task> getAllTasks() {
        List<Task> taskList = iservice.getAllTasks();
        return taskList;
    }

    @DeleteMapping("/removetask/{idtask}")
    @ResponseBody
    public void removeTask(@PathVariable("idtask") Long idtask) {
        iservice.removeTask(idtask);
    }


    @PostMapping("/add-task/{projectId}")
    public Task addTaskAndAssignToProject(@PathVariable Long projectId, @RequestBody Task task) {
        Task addedTask = iservice.addTaskAndAssignToProject(projectId, task);
        return addedTask;
    }

    /* @PostMapping("/assignProject/{projectId}")
     public Consultant addConsultantAndAssignToProject(@PathVariable Long projectId, @RequestBody Consultant consultant) {
         Consultant addedConsultant = iservice.addConsultantAndAssignToProject(projectId, consultant);
         return addedConsultant ;
     }*/
    @PutMapping("/updatetask/{idTask}")
    @ResponseBody
    public Task updateTask(@PathVariable("idTask") Long idTask, @RequestBody Task t) {
        Task task = iservice.updateTask(idTask, t);
        return task;
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return iservice.getTaskById(id);
    }


    @PostMapping("/AssignTaskToEmployee/{id_employe}/{taskId}")
    public void AssignTaskToEmployee(@PathVariable Long id_employe, @PathVariable Long taskId) {
        iservice.AssignTaskToEmployee(id_employe, taskId);
    }

    @GetMapping("/gettaskbyemployee/{username}")
    @ResponseBody
    List<Task> getAllTasksByEmployee(@PathVariable String username) {
        List<Task> taskListemp = iservice.getAllTasksByEmployee(username);
        return taskListemp;
    }

    @GetMapping("/gettaskbyproject/{projectId}")
    @ResponseBody
    List<Task> getTasksByProject(@PathVariable Long projectId) {
        List<Task> taskListpr = iservice.getTasksByProject(projectId);
        return taskListpr;
    }

    @PutMapping("/calculate-cost/{projectId}")
    public double calculCostProject(@PathVariable Long projectId) {
       return iservice.calculCostProject(projectId);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return iservice.getProjectById(id);
    }


    @GetMapping("/average-profitability")
    public double calculateAverageProfitability() {
        return iservice.calculateAverageProfitability();
    }

    @GetMapping("/statisticsByType")
    public ResponseEntity<?> getStatisticsByType() {
        return iservice.calculateStatisticsByType();
    }
    @GetMapping("/calculateProfitability")
    public List<Object[]> calculateProfitabilityForEachProject() {
        return iservice.calculateProfitabilityForEachProject();
    }

    @GetMapping("/best-project")
    public ResponseEntity<?> getBestProjectOfTheYear(){
        return iservice.getBestProjectOfTheYear();
    }

}