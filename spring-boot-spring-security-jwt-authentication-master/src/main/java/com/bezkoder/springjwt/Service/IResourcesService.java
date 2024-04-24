package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.Project;
import com.bezkoder.springjwt.models.ResourceQuantityDTO;
import com.bezkoder.springjwt.models.Resources;

import java.util.List;
import java.util.Map;

public interface IResourcesService {
    List<Resources> retrieveAllResources();
    Resources addResource(Resources b);
    Resources updateResource(Resources b);
    Resources retrieveResource(Long idRes);
    void removeResource(Long idRes);
    void affectResourcesToProject(Long projectId, List<ResourceQuantityDTO> resourceQuantities);

    public List<Project> retrieveAllProjects();
    public List<List<Object>> getResourceStockList();
}
