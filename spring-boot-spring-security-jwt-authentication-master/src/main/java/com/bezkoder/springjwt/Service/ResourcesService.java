package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.ProjectRepository;
import com.bezkoder.springjwt.repository.ResoucesRepository;
import com.bezkoder.springjwt.repository.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class ResourcesService implements IResourcesService {

    ResoucesRepository resoucesRepository;
    StockRepository stockRepository;
    ProjectRepository projectRepository;


    @Override
    public List<Resources> retrieveAllResources() {
        return resoucesRepository.findAll();
    }

    @Override
    public List<Project> retrieveAllProjects() {
        return projectRepository.findAll();
    }




    @Override
    public Resources addResource(Resources r) {
        List<Stock> availableStocks = stockRepository.findAvailableStocksByCategorieStockAndResourceIsNull(r.getCategorie());

        if (!availableStocks.isEmpty()) {
            Stock stock = availableStocks.get(0);

            stock.setResource(r);
            r.setReStatus(ResourceStatus.ACTIVE);
            return resoucesRepository.save(r);
        } else {
            throw new RuntimeException("No available stock found for category: " + r.getCategorie());
        }
    }

    @Override
    public Resources updateResource(Resources r) {
        return resoucesRepository.save(r);
    }

    @Override
    public Resources retrieveResource(Long idRes) {
        return resoucesRepository.findById(idRes).orElse(null);
    }

    @Override
    public void removeResource(Long idRes) {
        log.debug("debugging");
        resoucesRepository.deleteById(idRes);

    }

    @Override
    public void affectResourcesToProject(Long projectId, List<ResourceQuantityDTO> resourceQuantities) {
        // Récupérer le projet
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        // Pour chaque ressource demandée
        for (ResourceQuantityDTO resourceQuantity : resourceQuantities) {
            Long resourceId = resourceQuantity.getResourceId();
            Integer quantityRequested = resourceQuantity.getQuantity();

            // Récupérer la ressource
            Resources resource = resoucesRepository.findById(resourceId)
                    .orElseThrow(() -> new EntityNotFoundException("Resource not found with id: " + resourceId));

            // Vérifier la quantité disponible dans le stock
            Stock stock = resource.getStock();
            if (stock.getQuantity() < quantityRequested) {
                throw new RuntimeException("Insufficient stock for resource: " + resource.getName());
            }

            // Mettre à jour la quantité de stock
            stock.setQuantity(stock.getQuantity() - quantityRequested);
            stockRepository.save(stock);
            log.info("Stock updated for resource: {}, new quantity: {}", resource.getName(), stock.getQuantity());

            // Ajouter la ressource au projet
            project.getResources().add(resource);
            log.info("Resource added to project: {}", resource.getName());
        }

        // Enregistrer les modifications du projet dans la base de données
        projectRepository.save(project);
        log.info("Project updated with resources: {}", project.getResources());
    }

    @Override
    public List<List<Object>> getResourceStockList() {
        List<List<Object>> resourceStockList = new ArrayList<>();

        Iterable<Resources> resources = resoucesRepository.findAll();

        for (Resources resource : resources) {
            if (resource.getStock() != null) {
                List<Object> resourceStock = new ArrayList<>();
                resourceStock.add(resource.getName()); // Suppose resource.getName() retourne le nom de la ressource
                resourceStock.add(resource.getStock().getQuantity());
                resourceStockList.add(resourceStock);
            }
        }

        return resourceStockList;
    }


}