package com.portfolio.Repository;

import com.portfolio.Entity.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RTag extends JpaRepository<Tag, Integer> {

    public Optional<Tag> findByName(String name);

    public List<Tag> findAllByEducationsId(int id);

    public boolean existsByName(String name);

}
