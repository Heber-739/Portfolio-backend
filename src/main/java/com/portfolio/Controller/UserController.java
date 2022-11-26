package com.portfolio.Controller;

import com.portfolio.Dto.DtoUser;
import com.portfolio.Entity.User;
import com.portfolio.Security.Controller.Message;
import com.portfolio.Service.ImplementUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    ImplementUserService iUserService;

    @GetMapping("/get")
    public User getUserDefault() {
        return iUserService.findUser("Heber739");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get/{id}")
    public User getUser(@PathVariable("id") String id) {
        return iUserService.findUser(id);
    }

    //Solo el admin puede ver a los usuarios registrados
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll")
    public List<User> getAll() {
        return iUserService.getUsers();
    }

    //Debe estar registrado para crear un usuario
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<Message> createUser(@RequestBody DtoUser user) {
        if (iUserService.getUsers().size() > 6) {
            return new ResponseEntity<Message>(new Message("Máximo de usuarios alcanzado"), HttpStatus.OK);
        }

        if (iUserService.existsByGithub(user.getGithub())) {
            return new ResponseEntity<Message>(new Message("Ya existe el github ingresado"), HttpStatus.BAD_REQUEST);
        }
        if (iUserService.existsByLinkedin(user.getLinkedin())) {
            return new ResponseEntity<Message>(new Message("Ya existe el linkedin ingresado"), HttpStatus.BAD_REQUEST);
        }

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setSurname(user.getSurname());
        newUser.setUsername(user.getUsername());
        newUser.setAge(user.getAge());
        newUser.setImg(user.getImg());
        newUser.setType_img(user.getType_img());
        newUser.setDescription(user.getDescription());
        newUser.setLinkedin(user.getLinkedin());
        newUser.setGithub(user.getGithub());
        iUserService.saveUser(newUser);
        return new ResponseEntity<Message>(new Message("Usuario creado"), HttpStatus.OK);

    }

    //Solo el admin puede eliminar un usuario
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteUser(@PathVariable String id) {
        iUserService.deleteUser(id);
        return new ResponseEntity<Message>(new Message("Usuario eliminado"), HttpStatus.OK);
    }

    //los datos de usuario
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/edith/{id}")
    public ResponseEntity<Message> editUser(@PathVariable String id, @RequestBody DtoUser edithUser) {
        if (iUserService.existsByGithub(edithUser.getGithub())) {
            User us = iUserService.findByGithub(edithUser.getGithub());
            if (!us.getUsername().equals(id)) {
                return new ResponseEntity<Message>(new Message("Ya existe el github ingresado"), HttpStatus.BAD_REQUEST);
            }

        }
        if (iUserService.existsByLinkedin(edithUser.getLinkedin())) {
            User uss = iUserService.findByLinkedin(edithUser.getLinkedin());
            if (!uss.getUsername().equals(id)) {
                return new ResponseEntity<Message>(new Message("Ya existe el linkedin ingresado"), HttpStatus.BAD_REQUEST);
            }
        }
        //String type_img = file.getContentType();
        //byte[] img = file.getBytes();

        User user = iUserService.findUser(id);
        user.setName(edithUser.getName());
        user.setUsername(edithUser.getUsername());
        user.setAge(edithUser.getAge());
        user.setImg(edithUser.getImg());
        user.setType_img(edithUser.getType_img());
        user.setDescription(edithUser.getDescription());
        user.setLinkedin(edithUser.getLinkedin());
        user.setGithub(edithUser.getGithub());
        iUserService.saveUser(user);
        return new ResponseEntity<Message>(new Message("Usuario editado correctamente"), HttpStatus.OK);

    }

}
