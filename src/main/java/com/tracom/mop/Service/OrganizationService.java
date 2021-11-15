package com.tracom.mop.Service;

import com.tracom.mop.Entity.Organization;
import com.tracom.mop.Repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    private OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository){
        this.organizationRepository= organizationRepository;
    }
    //CREATE
    public void saveOrganization(Organization organization){
        organizationRepository.save(organization);
    }

    //RETRIEVE
    public List<Organization> listOrganization(){
        return organizationRepository.findAll();
    }

    // DELETE
    public void deleteOrganization(int id){
        organizationRepository.deleteById(id);
    }

    //UPDATE
    public Organization updateOrganization(int id){
        return organizationRepository.findById(id).get();
    }
}
