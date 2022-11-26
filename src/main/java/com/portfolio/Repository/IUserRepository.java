package com.portfolio.Repository;

import com.portfolio.Entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {

    public boolean existsByLinkedin(String linkedin);

    public boolean existsByGithub(String github);

    public Optional<User> findByLinkedin(String linkedin);

    public Optional<User> findByGithub(String github);

    public boolean existsByUsername(String username);

    public Optional<User> findByUsername(String username);
}
