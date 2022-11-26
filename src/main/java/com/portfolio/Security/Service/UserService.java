package com.portfolio.Security.Service;

import com.portfolio.Security.Entity.PersonUser;
import com.portfolio.Security.Repository.IUserServiceRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    @Autowired
    IUserServiceRepository iUserRepository;

    public List<PersonUser> getAll() {
        return iUserRepository.findAll();
    }

    public Optional<PersonUser> getByUsername(String username) {
        return iUserRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return iUserRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return iUserRepository.existsByEmail(email);
    }

    public void save(PersonUser personUser) {
        iUserRepository.save(personUser);
    }

}
