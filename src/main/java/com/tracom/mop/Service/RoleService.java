package com.tracom.mop.Service;

import com.tracom.mop.Entity.Role;
import com.tracom.mop.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    //CREATE
    public void saveRole(Role role){
        roleRepository.save(role);
    }

    //RETRIEVE
    public List<Role> listRoles(){
        return roleRepository.findAll();
    }
    // DELETE
    public void deleteRole(int id){
        roleRepository.deleteById(id);
    }

    //UPDATE
    public Role updateRole(int id){
        return roleRepository.findById(id).get();
    }
}
