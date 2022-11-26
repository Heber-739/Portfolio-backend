package com.portfolio.Service;

import com.portfolio.Entity.Education;
import com.portfolio.Repository.REducation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SEducation {

    @Autowired
    REducation rEducation;

    public List<Education> list() {
        return rEducation.findAll();
    }

    public Education findById(int id) {
        return rEducation.findById(id).orElse(null);
    }

    public Education findByName(String name) {
        return rEducation.findByName(name).orElse(null);
    }

    public void save(Education education) {
        rEducation.save(education);
    }

    public void delete(int id) {
        rEducation.deleteById(id);
    }

    public boolean existsById(int id) {
        return rEducation.existsById(id);
    }

    public Education findByLink(String link) {
        return rEducation.findByLink(link).orElse(null);
    }

    public boolean existsByLink(String link) {
        return rEducation.existsByLink(link);
    }

    public boolean existsByName(String name) {
        return rEducation.existsByName(name);
    }

    public List<Education> findAllByUserUsername(String username) {
        return rEducation.findAllByUserUsername(username);
    }
}
