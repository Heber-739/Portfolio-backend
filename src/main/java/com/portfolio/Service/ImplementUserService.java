package com.portfolio.Service;

import com.portfolio.Entity.User;
import com.portfolio.Repository.IUserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ImplementUserService {

    @Autowired
    IUserRepository iUserRepository;

    public List<User> getUsers() {
        return iUserRepository.findAll();
    }

    public void saveUser(User user) {
        iUserRepository.save(user);
    }

    public void deleteUser(String id) {
        iUserRepository.deleteById(id);

    }

    public User findUser(String username) {
        return iUserRepository.findByUsername(username).orElse(null);
    }

    public boolean existsByUsername(String username) {
        return iUserRepository.existsByUsername(username);
    }

    public boolean existsByLinkedin(String linkedin) {
        return iUserRepository.existsByLinkedin(linkedin);
    }

    public boolean existsByGithub(String github) {
        return iUserRepository.existsByGithub(github);
    }

    public User findByLinkedin(String linkedin) {
        return iUserRepository.findByLinkedin(linkedin).orElse(null);
    }

    public User findByGithub(String github) {
        return iUserRepository.findByGithub(github).orElse(null);
    }

}
