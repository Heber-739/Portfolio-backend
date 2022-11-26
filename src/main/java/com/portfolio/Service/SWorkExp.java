package com.portfolio.Service;

import com.portfolio.Entity.WorkExperience;
import com.portfolio.Repository.RWorkExperience;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SWorkExp {

    @Autowired
    RWorkExperience rWorkExperience;

    public List<WorkExperience> list() {
        return rWorkExperience.findAll();
    }

    public List<WorkExperience> findAllByUserUsername(String username) {
        return rWorkExperience.findAllByUserUsername(username);
    }

    public WorkExperience findById(int id) {
        return rWorkExperience.findById(id).orElse(null);
    }

    public void save(WorkExperience workExperience) {
        rWorkExperience.save(workExperience);
    }

    public void delete(int id) {
        rWorkExperience.deleteById(id);
    }

    public boolean existsById(int id) {
        return rWorkExperience.existsById(id);
    }

    public boolean existsByName(String name) {
        return rWorkExperience.existsByName(name);
    }

}
