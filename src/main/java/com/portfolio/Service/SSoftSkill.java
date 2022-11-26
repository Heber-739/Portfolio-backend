package com.portfolio.Service;

import com.portfolio.Entity.SoftSkill;
import com.portfolio.Repository.RSoftSkill;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SSoftSkill {

    @Autowired
    RSoftSkill rSoftSkill;

    public List<SoftSkill> list() {
        return rSoftSkill.findAll();
    }

    public List<SoftSkill> findAllByUsersUsername(String username) {
        return rSoftSkill.findAllByUsersUsername(username);
    }

    public SoftSkill findById(int id) {
        return rSoftSkill.findById(id).orElse(null);
    }

    public List<SoftSkill> findAllByName(String name) {
        return rSoftSkill.findAllByName(name);
    }

    public void save(SoftSkill softSkill) {
        rSoftSkill.save(softSkill);
    }

    public void delete(int id) {
        rSoftSkill.deleteById(id);
    }

    public boolean existsById(int id) {
        return rSoftSkill.existsById(id);
    }

    public boolean existsByName(String name) {
        return rSoftSkill.existsByName(name);
    }

}
