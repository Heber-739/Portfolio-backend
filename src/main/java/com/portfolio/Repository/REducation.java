package com.portfolio.Repository;

import com.portfolio.Entity.Education;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface REducation extends JpaRepository<Education, Integer> {

    public Optional<Education> findByName(String name);

    public Optional<Education> findByLink(String link);

    public boolean existsByLink(String link);

    public boolean existsByName(String name);

    public List<Education> findAllByUserUsername(String username);

}
