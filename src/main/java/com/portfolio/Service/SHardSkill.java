package com.portfolio.Service;

import com.portfolio.Entity.HardSkill;
import com.portfolio.Repository.RHardSkill;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SHardSkill {

    @Autowired
    RHardSkill rHardSkill;

    public List<HardSkill> list() {
        return rHardSkill.findAll();
    }

    public List<HardSkill> findAllByUsersUsername(String username) {
        return rHardSkill.findAllByUsersUsername(username);
    }

    public HardSkill findById(int id) {
        return rHardSkill.findById(id).orElse(null);
    }

    public List<HardSkill> findAllByName(String name) {
        return rHardSkill.findAllByName(name);
    }

    public void save(HardSkill hardSkill) {
        rHardSkill.save(hardSkill);
    }

    public void delete(int id) {
        rHardSkill.deleteById(id);
    }

    public boolean existsById(int id) {
        return rHardSkill.existsById(id);
    }

    public boolean existsByName(String name) {
        return rHardSkill.existsByName(name);
    }

}
