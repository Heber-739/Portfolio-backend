package com.portfolio.Controller;

import com.portfolio.Dto.DtoWorkExp;
import com.portfolio.Entity.User;
import com.portfolio.Entity.WorkExperience;
import com.portfolio.Security.Controller.Message;
import com.portfolio.Service.ImplementUserService;
import com.portfolio.Service.SWorkExp;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/workExperience")
public class WorkExpController {

    @Autowired
    SWorkExp expService;

    @Autowired
    ImplementUserService implementUserService;

    // Solo el admin debe ver las experiencias personales
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listAll")
    public ResponseEntity<?> listAll() {
        if (expService.list().isEmpty()) {
            return new ResponseEntity<Message>(new Message("No existen experiencias en la base de datos"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<WorkExperience>>(expService.list(), HttpStatus.OK);
    }

    //Listamos las experiencias en el componente
    @GetMapping("/list/{id}")
    public ResponseEntity<?> list(@PathVariable("id") String username) {
        if (!implementUserService.existsByUsername(username)) {
            return new ResponseEntity<Message>(new Message("Usuario no encontrado"), HttpStatus.NOT_FOUND);
        }
        if (expService.findAllByUserUsername(username).isEmpty()) {
            return new ResponseEntity<Message>(new Message("El usuario no posee experiencias"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<WorkExperience>>(expService.findAllByUserUsername(username), HttpStatus.OK);
    }

    //Eliminamos una experiencia
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}/{user_id}")
    public ResponseEntity<Message> delete(@PathVariable("id") int id, @PathVariable("user_id") String username) {
        if (!expService.existsById(id)) {
            return new ResponseEntity<Message>(new Message("No existe"), HttpStatus.NOT_FOUND);
        }

        User user = implementUserService.findUser(username);
        user.removeWorkExp(id);
        implementUserService.saveUser(user);
        expService.delete(id);
        return new ResponseEntity<Message>(new Message("producto eliminado"), HttpStatus.OK);
    }

    //Solo un usuario podrá crear una experiencia
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create/{user_id}")
    public ResponseEntity<Message> create(@PathVariable("user_id") String username, @RequestBody DtoWorkExp dtoExp) {
        if (StringUtils.isBlank(dtoExp.getName()) || StringUtils.isBlank(dtoExp.getDescription())) {
            return new ResponseEntity<Message>(new Message("No se aceptan campos vacíos"), HttpStatus.BAD_REQUEST);
        }
        if (!implementUserService.existsByUsername(username)) {
            return new ResponseEntity<Message>(new Message("La experiencia ingresada no se puede vincular a un usuario inexistente"), HttpStatus.BAD_REQUEST);
        }
        List<String> works = expService.findAllByUserUsername(username).stream().map(exp -> exp.getName()).collect(Collectors.toList());

        if (works.contains(dtoExp.getName())) {
            return new ResponseEntity<Message>(new Message("El usuario ya posee este item"), HttpStatus.BAD_REQUEST);
        }
        User user = implementUserService.findUser(username);
        WorkExperience wExperience = new WorkExperience(dtoExp.getName(), dtoExp.getDescription(), implementUserService.findUser(username));
        user.addWork(wExperience);
        expService.save(wExperience);
        return new ResponseEntity<Message>(new Message("Experiencia agregada"), HttpStatus.OK);
    }

    //Solo un usuario podrá actualizar una experiencia y debe pertenecer al usuario
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/{id}/{user_id}")
    public ResponseEntity<Message> update(@PathVariable("id") int id, @PathVariable("user_id") String username, @RequestBody DtoWorkExp dtoExp) {

        if (!implementUserService.existsByUsername(username)) {
            return new ResponseEntity<Message>(new Message("Usuario no encontrado"), HttpStatus.NOT_FOUND);
        }
        if (StringUtils.isBlank(dtoExp.getName()) || StringUtils.isBlank(dtoExp.getDescription())) {
            return new ResponseEntity<Message>(new Message("No se aceptan campos vacíos"), HttpStatus.BAD_REQUEST);
        }
        if (!expService.existsById(id)) {
            return new ResponseEntity<Message>(new Message("Experiencia no encontrada"), HttpStatus.NOT_FOUND);
        }
        List<Integer> ids = expService.findAllByUserUsername(username).stream().map(i -> i.getId()).collect(Collectors.toList());
        if (!ids.contains(id)) {
            return new ResponseEntity<Message>(new Message("La experiencia que trata de actualizar no corresponde al usuario ingresado"), HttpStatus.OK);
        }
        WorkExperience userExp = expService.findById(id);
        userExp.setName(dtoExp.getName());
        userExp.setDescription((dtoExp.getDescription()));
        userExp.setUser(implementUserService.findUser(username));

        expService.save(userExp);
        return new ResponseEntity<Message>(new Message("Experiencia actualizada"), HttpStatus.OK);

    }

}
