package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ServiceImp implements Iservice {
    ConsultantRepository consultantRepository;
    ProjectRepository projectRepository;
    TaskRepository taskRepository;
    TeamRepository teamRepository;
    EmployeeRepository employeeRepository;

    public Project addProject(Project p) {
        return projectRepository.save(p);
    }


    public Project updateProject(Long projectId, Project updatedProject) {
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Aucun projet trouvé avec l'ID : " + projectId));

        // Mettre à jour les champs du projet existant avec les nouvelles valeurs
        existingProject.setProjectname(updatedProject.getProjectname());
        existingProject.setProjectdescription(updatedProject.getProjectdescription());
        existingProject.setStartDate(updatedProject.getStartDate());
        existingProject.setEndDate(updatedProject.getEndDate());
        existingProject.setStatus(updatedProject.getStatus());
        existingProject.setType(updatedProject.getType());
        existingProject.setBudget(updatedProject.getBudget());
        // Mettre à jour d'autres champs selon vos besoins

        // Enregistrer les modifications dans la base de données
        return projectRepository.save(existingProject);
    }

    public void removeProject(Long project_id) {
        log.debug("debugging");
        projectRepository.deleteById(project_id);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public ResponseEntity<Project> getProjectById(Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            return new ResponseEntity<>(projectOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public Task addTaskAndAssignToProject(Long projectId, Task task) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Aucun projet trouvé avec l'ID : " + projectId));

        task.setProject(project);
        // Enregistrer la tâche dans la base de données
        Task savedTask = taskRepository.save(task);
        // Ajouter la tâche à la liste des tâches du projet
        project.getTasks().add(savedTask);

        projectRepository.save(project);
        // Retourner la tâche ajoutée
        return savedTask;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }


    //Employee employee = employeeRepository.findById(id_employe).orElseThrow();
    // Team team = project.getTeam();
    // employee.setTeams(team);
    // task.setProject(project);
    //if (team == null) {
    // log.info("team with name "+ team.getTeam_name()+" not found ");
    // }
    //task.setOwner(employee.getId_employe());



    public Task updateTask(Long taskId, Task updatedTask) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Aucune tache trouvé avec l'ID : " + taskId));

        // Mettre à jour les champs du tache existant avec les nouvelles valeurs
        existingTask.setTaskname(updatedTask.getTaskname());
        existingTask.setTaskdescription(updatedTask.getTaskdescription());
        existingTask.setOwner(updatedTask.getOwner());
        existingTask.setStartDate(updatedTask.getStartDate());
        existingTask.setEndDate(updatedTask.getEndDate());
        existingTask.setDuration(updatedTask.getDuration());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setProgression(updatedTask.getProgression());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setTypeDependance(updatedTask.getTypeDependance());
        existingTask.setProject(updatedTask.getProject());
        // Mettre à jour d'autres champs selon vos besoins

        // Enregistrer les modifications dans la base de données
        return taskRepository.save(existingTask);
    }

    public ResponseEntity<Task> getTaskById(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            return new ResponseEntity<>(taskOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }





    public void deleteTask(Long taskId) {
        Task task = null;
        try {
            task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
        // Supprimer la tâche de la liste des tâches du projet
        task.getProject().getTasks().remove(task);
        // Supprimer la tâche de la base de données
        taskRepository.delete(task);
    }

    public void removeTask(Long taskId) {
        log.debug("debugging");
        taskRepository.deleteById(taskId);
    }


   /* public Consultant addConsultantAndAssignToProject(Long projectId, Consultant consultant) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Aucun projet trouvé avec l'ID : " + projectId));
        List<Project> projects = new ArrayList<>();
        if (consultant.getProjects() != null) {
            consultant.getProjects().add(project);
        } else {
            projects.add(project);
            consultant.setProjects(projects);
        }
        // Affecter le consultant au projet
        project.setConsultant(consultant);
        return consultantRepository.save(consultant);
    }*/


    public void AssignTaskToEmployee(Long id_employe, Long taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Aucun projet trouvé avec l'ID : " + taskId));

        if (task.getProject() != null && task.getProject().getTeam() != null && task.getProject().getTeam().getEmployees() != null) {

            List<Employee> employees = task.getProject().getTeam().getEmployees();

            // Vérifier si la liste d'employés est non nulle et non vide
            if (employees != null && !employees.isEmpty()) {
                // Recherche de l'employé correspondant à l'ID fourni
                Optional<Employee> employeeOptional = employees.stream()
                        .filter(employee -> employee.getId_employe().equals(id_employe))
                        .findFirst();
                // Vérifier si l'employé a été trouvé
                if (employeeOptional.isPresent()) {
                    // Obtenir le nom d'utilisateur de l'employé
                    String employeeUsername = employeeOptional.get().getUsername();
                    log.info("username de l'employé : {}", employeeUsername);
                    // Définir le nom d'utilisateur de l'employé comme propriétaire de la tâche
                    task.setOwner(employeeUsername);

                    // Enregistrer la tâche mise à jour dans la base de données
                    taskRepository.save(task);

                } else {
                    throw new NoSuchElementException("Aucun employé trouvé avec l'ID : " + id_employe);
                }

            }

        }}
        public List<Task> getAllTasksByEmployee(String username ) {
            List<Task> tasks = taskRepository.findAll();
            List<Task> tasksByEmployee = new ArrayList<>();

            for (Task t : tasks) {
                if (t.getOwner() != null && t.getOwner().equals(username)) {
                    tasksByEmployee.add(t);
                }
            }

            return tasksByEmployee;
        }

        public List<Task> getTasksByProject(Long projectId) {

        return taskRepository.findByProjectProjectid(projectId);
        }

     public double calculCostProject (Long projectId) {
         Project project = projectRepository.findById(projectId)
                 .orElseThrow(()
                         -> new NoSuchElementException("Aucun projet trouvé avec l'ID : " + projectId));
         double totalCost = 0.0;

         // Calculer le coût des consultants
         List<Consultant> consultants = project.getConsultants();
         for (Consultant consultant : consultants) {
             totalCost += consultant.getHourlyRate() * consultant.getHoursWorked();
         }
         // Calculer le coût des ressources
         List<Resources> resources = project.getResources();
         for (Resources resource : resources) {
             totalCost += resource.getPrice();
         }
         // Mettre à jour le coût du projet
         project.setCost(totalCost);
         // Sauvegarder les modifications dans la base de données
         projectRepository.save(project);

         return totalCost;
     }


    public double calculateAverageProfitability() {
        List<Project> projects = projectRepository.findAll();
        double totalProfitability = 0;
        int numberOfProjects = projects.size();

        for (Project project : projects) {
            // Calculer la rentabilité pour chaque projet
            double profitability = project.getExpectedRevenue() - project.getCost();
            // Ajouter la rentabilité du projet au total
            totalProfitability += profitability;
        }

        // Calculer la rentabilité moyenne en divisant le total par le nombre de projets
        if (numberOfProjects > 0) {
            return totalProfitability / numberOfProjects;
        } else {
            return 0; // Ou une valeur par défaut appropriée si aucun projet n'est disponible
        }
    }


    public ResponseEntity<?> calculateStatisticsByType() {
        List<Project> projects = projectRepository.findAll();
        double totalProjects = projects.size();
        if (totalProjects == 0) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "There are no projects available");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        Map<ProjectType, Long> statisticsByProjectType = projects.stream()
                .collect(Collectors.groupingBy(Project::getType, Collectors.counting()));

        return ResponseEntity.ok(statisticsByProjectType);
    }

    public List<Object[]> calculateProfitabilityForEachProject() {
        List<Project> projects = projectRepository.findAll();
        List<Object[]> profitabilityList = new ArrayList<>();

        for (Project project : projects) {
            double profitability = project.getExpectedRevenue() - project.getCost();
            profitabilityList.add(new Object[]{project, profitability});
        }

        return profitabilityList;
    }

    public ResponseEntity<?> getBestProjectOfTheYear() {
        List<Project> projects = projectRepository.findAll();
        Project bestProject = null;
        double maxProfitability = Double.MIN_VALUE;

        // Parcourir tous les projets pour trouver le meilleur projet
        for (Project project : projects) {
            double profitability = project.getExpectedRevenue() - project.getCost();
            if (profitability > maxProfitability) {
                maxProfitability = profitability;
                bestProject = project;
            }
        }

        // Vérifier si un meilleur projet a été trouvé
        if (bestProject != null) {
            return ResponseEntity.ok(bestProject);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Consultant addAndAssignConsultantToProjects(Consultant consultant, List<Long> projectIds) {
        if (consultant == null || projectIds == null || projectIds.isEmpty()) {
            throw new IllegalArgumentException("Consultant and at least one project must be provided.");
        }

        // Récupérer les projets à partir de leurs IDs
        List<Project> projects = projectRepository.findAllById(projectIds);

        // Mettre à jour la liste des consultants pour chaque projet
        for (Project project : projects) {
            if (project.getConsultants() == null) {
                project.setConsultants(new ArrayList<>());
            }
            project.getConsultants().add(consultant);
        }

        // Mettre à jour la liste des projets pour le consultant
        if (consultant.getProjects() == null) {
            consultant.setProjects(new ArrayList<>());
        }
        consultant.getProjects().addAll(projects);

        // Enregistrer le consultant mis à jour
        Consultant savedConsultant = consultantRepository.save(consultant);

        // Retourner le consultant avec les projets affectés
        return savedConsultant;
    }

    public Consultant addConsultant(Consultant C) {
        return consultantRepository.save(C);
    }

    public void assignConsultantsToProject(Long projectId, List<Long> consultantIds) {
        // Récupérer le projet par son identifiant
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            // Gérer le cas où le projet n'existe pas
            throw new IllegalArgumentException("Le projet avec l'identifiant " + projectId + " n'existe pas.");
        }

        // Récupérer les consultants par leurs identifiants
        List<Consultant> consultants = consultantRepository.findAllById(consultantIds);

        // Affecter les consultants au projet
        project.setConsultants(consultants);

        // Enregistrer les modifications dans la base de données
        projectRepository.save(project);
    }

    public void deleteConsultant(Long id) {
        Consultant consultant = consultantRepository.findById(id).orElse(null);
        if (consultant == null) {
            // Si le consultant n'existe pas, ne rien faire
            return;
        }
        // Retirer le consultant de tous les projets auxquels il est associé
        consultant.getProjects().forEach(project -> project.getConsultants().remove(consultant));
        // Supprimer le consultant lui-même
        consultantRepository.delete(consultant);
    }




    public Consultant updateConsultant(Long id, Consultant updatedConsultant) {
        Consultant existingConsultant = consultantRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Aucun consultant trouvé avec l'ID : " + id));
        if (existingConsultant != null) {
            // Mettre à jour les champs du consultant existant avec les nouvelles valeurs
            existingConsultant.setName(updatedConsultant.getName());
            existingConsultant.setAvailability(updatedConsultant.isAvailability());
            existingConsultant.setSkills(updatedConsultant.getSkills());
            existingConsultant.setHourlyRate(updatedConsultant.getHourlyRate());
            existingConsultant.setHoursWorked(updatedConsultant.getHoursWorked());

            // Enregistrer les modifications dans la base de données
            consultantRepository.save(existingConsultant);
        } else {
            throw new IllegalArgumentException("Consultant with ID " + id + " not found");
        }
        return existingConsultant;
    }




    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAll();
    }


    public List<Consultant> getConsultantsByProject(Long projectId) {

        return consultantRepository.findByProjectsProjectid(projectId);
    }


    public ResponseEntity<Consultant> getConsultantById(Long id) {
        Optional<Consultant> consultantOptional = consultantRepository.findById(id);
        if (consultantOptional.isPresent()) {
            return new ResponseEntity<>(consultantOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    public double calculateProjectProgression(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Projet non trouvé"));

        List<Task> tasks = project.getTasks();
        int totalTasks = tasks.size();
        if (totalTasks == 0) {
            return 0.0;
        }

        int totalProgress = 0;
        for (Task task : tasks) {
            totalProgress += task.getProgression();
        }
        return ((double) totalProgress / totalTasks);
    }

    public List<Object[]> calculateProfitabilityByYear() {
        List<Project> projects = projectRepository.findAll();
        Map<Integer, Double> profitabilityByYearMap = new HashMap<>();

        // Parcourir tous les projets pour calculer la rentabilité par année
        for (Project project : projects) {
            LocalDate startDate = project.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = project.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            int startYear = startDate.getYear();
            int endYear = endDate.getYear();

            // Ajouter la rentabilité du projet à la rentabilité totale de chaque année
            for (int year = startYear; year <= endYear; year++) {
                double profitability = project.getExpectedRevenue() - project.getCost();
                profitabilityByYearMap.merge(year, profitability, Double::sum);
            }
        }

        // Convertir la map en liste d'objets pour le retour
        List<Object[]> profitabilityByYearList = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : profitabilityByYearMap.entrySet()) {
            profitabilityByYearList.add(new Object[]{entry.getKey(), entry.getValue()});
        }

        return profitabilityByYearList;
    }


}
















    /*public void AssignTaskToEmployee(Long id_employe, Long taskId) {
        // Recherche de la tâche par son ID
        Task task = null;
        for (Project project : getAllProjects()) {
            for (Task t : project.getTasks()) {
                if (t.getTaskid().equals(taskId)) {
                    task = t;
                    break;
                }
            }
            if (task != null) {
                break;
            }
        }

        // Vérifier si la tâche a été trouvée
        if (task != null) {
            Project project = task.getProject();
            // Vérifier si le projet de la tâche est nul
            if (project != null) {
                Team team = project.getTeam();
                // Vérifier si l'équipe du projet est nulle
                if (team != null) {
                    List<Employee> employees = team.getEmployees();
                    // Vérifier si la liste des employés de l'équipe est nulle ou vide
                    if (employees != null && !employees.isEmpty()) {
                        // Recherche de l'employé correspondant à l'ID fourni
                        Employee employee = null;
                        for (Employee emp : employees) {
                            if (emp.getId_employe().equals(id_employe)) {
                                employee = emp;
                                break;
                            }
                        }

                        // Si l'employé existe, attribuer la tâche à l'employé
                        if (employee != null) {
                            task.setOwner(employee.getUsername());
                            taskRepository.save(task);
                            log.info("La tâche a été attribuée à l'employé avec succès.");
                        } else {
                            throw new NoSuchElementException("Aucun employé trouvé avec l'ID : " + id_employe);
                        }
                    } else {
                        throw new IllegalStateException("La liste des employés de l'équipe est nulle ou vide.");
                    }
                } else {
                    throw new IllegalStateException("L'équipe du projet est nulle.");
                }
            } else {
                throw new IllegalStateException("Le projet de la tâche est nul.");
            }
        } else {
            throw new NoSuchElementException("Aucune tâche trouvée avec l'ID : " + taskId);
        }*/

























