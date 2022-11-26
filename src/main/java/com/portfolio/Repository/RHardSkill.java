package com.portfolio.Repository;

import com.portfolio.Entity.HardSkill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RHardSkill extends JpaRepository<HardSkill, Integer> {

    public List<HardSkill> findAllByName(String name);

    public boolean existsByName(String name);

    public List<HardSkill> findAllByUsersUsername(String username);

}
