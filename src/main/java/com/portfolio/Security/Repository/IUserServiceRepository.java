
package com.portfolio.Security.Repository;

import com.portfolio.Security.Entity.PersonUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IUserServiceRepository extends JpaRepository<PersonUser, Integer>{
    Optional<PersonUser> findByUsername(String username);
    
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    
}
