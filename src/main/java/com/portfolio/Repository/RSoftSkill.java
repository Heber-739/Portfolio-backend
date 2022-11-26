package com.portfolio.Repository;

import com.portfolio.Entity.SoftSkill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RSoftSkill extends JpaRepository<SoftSkill, Integer> {

    public List<SoftSkill> findAllByName(String name);

    public boolean existsByName(String name);

    public List<SoftSkill> findAllByUsersUsername(String username);

}
