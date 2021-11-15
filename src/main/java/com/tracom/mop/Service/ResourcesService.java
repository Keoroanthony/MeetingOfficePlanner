package com.tracom.mop.Service;

import com.tracom.mop.Entity.Resources;
import com.tracom.mop.Repository.ResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourcesService {
    private ResourcesRepository resourcesRepository;
    @Autowired
    public ResourcesService(ResourcesRepository resourcesRepository){
        this.resourcesRepository = resourcesRepository;
    }
    //CREATE
    public void saveResource(Resources resources){
        resourcesRepository.save(resources);
    }
    //RETRIEVE
    public List<Resources> listResources(){
        return resourcesRepository.findAll();
    }
    public Resources getByResource_name(int id) {
        return resourcesRepository.findByResource_name(id);
    }
    // UPDATE
    public Resources updateResources(int id){
        return resourcesRepository.findById(id).get();
    }
    // DELETE
    public void deleteResource(int id){
        resourcesRepository.deleteById(id);
    }
}
