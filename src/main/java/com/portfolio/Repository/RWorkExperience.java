package com.portfolio.Repository;

import com.portfolio.Entity.WorkExperience;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RWorkExperience extends JpaRepository<WorkExperience, Integer> {

    public boolean existsByName(String name);

    public List<WorkExperience> findAllByUserUsername(String username);

}
