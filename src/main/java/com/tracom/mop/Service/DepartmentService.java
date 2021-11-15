package com.tracom.mop.Service;

import com.tracom.mop.Entity.Department;
import com.tracom.mop.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }

    //CREATE
    public void saveDepartment(Department department){
        departmentRepository.save(department);
    }

    //RETRIEVE
    public List<Department> listDepartment(){
        return departmentRepository.findAll();
    }

    // DELETE
    public void deleteDepartment(int id){
        departmentRepository.deleteById(id);
    }

    //UPDATE
    public Department updateDepartment(int id){
        return departmentRepository.findById(id).get();
    }
}
