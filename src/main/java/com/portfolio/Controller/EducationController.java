package com.portfolio.Controller;

import com.portfolio.Dto.DtoEducation;
import com.portfolio.Entity.Education;
import com.portfolio.Entity.User;
import com.portfolio.Security.Controller.Message;
import com.portfolio.Service.ImplementUserService;
import com.portfolio.Service.SEducation;
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
@RequestMapping("/education")
public class EducationController {

    @Autowired
    SEducation sEducation;

    @Autowired
    ImplementUserService implementUserService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listAll")
    public ResponseEntity<?> listAll() {
        if (sEducation.list().isEmpty()) {
            return new ResponseEntity<Message>(new Message("No existen educaciones en la base de datos"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Education>>(sEducation.list(), HttpStatus.OK);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id) {
        if (!sEducation.existsById(id)) {
            return new ResponseEntity<Message>(new Message("No se encuentra la educación"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Education>(sEducation.findById(id), HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> list(@PathVariable("id") String username) {
        if (!implementUserService.existsByUsername(username)) {
            return new ResponseEntity<Message>(new Message("No se encuentra el usuario ingresado"), HttpStatus.NOT_FOUND);
        }
        if (sEducation.findAllByUserUsername(username).isEmpty()) {
            return new ResponseEntity<Message>(new Message("Aún no tiene educaciones registradas"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Education>>(sEducation.findAllByUserUsername(username), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{ed_id}/{user_id}")
    public ResponseEntity<Message> delete(@PathVariable("ed_id") int id, @PathVariable("user_id") String user_id) {
        if (!sEducation.existsById(id)) {
            return new ResponseEntity<Message>(new Message("No se encuentra la educación que desea eliminar"), HttpStatus.NOT_FOUND);
        }
        List<Integer> ids = implementUserService.findUser(user_id).getEducation().stream().map(ed -> ed.getId()).collect(Collectors.toList());
        if (!ids.contains(id)) {
            return new ResponseEntity<Message>(new Message("No es posible eliminar esta educación"), HttpStatus.BAD_REQUEST);
        }

        User user = implementUserService.findUser(user_id);
        user.removeEducation(id);
        implementUserService.saveUser(user);
        sEducation.delete(id);

        return new ResponseEntity<Message>(new Message("Educación eliminada"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create/{user_id}")
    public ResponseEntity<Message> create(@PathVariable("user_id") String username, @RequestBody DtoEducation dtoEducation) {
        if (StringUtils.isBlank(dtoEducation.getName()) || StringUtils.isBlank(dtoEducation.getLink())) {
            return new ResponseEntity<Message>(new Message("Revise el campo en blanco"), HttpStatus.BAD_REQUEST);
        }
        if (!implementUserService.existsByUsername(username)) {
            return new ResponseEntity<Message>(new Message("La educación ingresada no se puede vincular a un usuario inexistente"), HttpStatus.BAD_REQUEST);
        }

        List<String> edus = sEducation.findAllByUserUsername(username).stream().map(ed -> ed.getName()).collect(Collectors.toList());
        if (edus.contains(dtoEducation.getName())) {
            return new ResponseEntity<Message>(new Message("El usuario ya posee esta educación"), HttpStatus.BAD_REQUEST);
        }
        User user = implementUserService.findUser(username);
        Education ed = new Education(dtoEducation.getName(), dtoEducation.getLink(), dtoEducation.isFinish(), dtoEducation.getImg(), dtoEducation.getType_img());
        user.addEducation(ed);
        sEducation.save(ed);
        return new ResponseEntity<Message>(new Message("Educación agregada"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/{ed_id}/{user_id}")
    public ResponseEntity<Message> update(@PathVariable("ed_id") int id, @PathVariable("user_id") String username, @RequestBody DtoEducation dtoEducation) {
        if (StringUtils.isBlank(dtoEducation.getName()) || StringUtils.isBlank(dtoEducation.getLink())) {
            return new ResponseEntity<Message>(new Message("Revise el campo en blanco"), HttpStatus.BAD_REQUEST);
        }
        //Verificacion de ID
        if (!sEducation.existsById(id)) {
            return new ResponseEntity<Message>(new Message("No se encuentra la educación"), HttpStatus.NOT_FOUND);
        }
        List<Integer> ids = implementUserService.findUser(username).getEducation().stream().map(ed -> ed.getId()).collect(Collectors.toList());
        if (!ids.contains(id)) {
            return new ResponseEntity<Message>(new Message("No es posible actualizar esta educación."), HttpStatus.BAD_REQUEST);
        }

        Education education = sEducation.findById(id);
        education.setName(dtoEducation.getName());
        education.setLink(dtoEducation.getLink());
        education.setFinish(dtoEducation.isFinish());
        education.setImg(dtoEducation.getImg());
        education.setType_img(dtoEducation.getType_img());
        sEducation.save(education);

        return new ResponseEntity<Message>(new Message("Educación actualizada"), HttpStatus.OK);
    }
}
