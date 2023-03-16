package com.portfolio.Controller;

import com.portfolio.Dto.DtoSoftSkill;
import com.portfolio.Entity.SoftSkill;
import com.portfolio.Entity.User;
import com.portfolio.Security.Controller.Message;
import com.portfolio.Service.ImplementUserService;
import com.portfolio.Service.SSoftSkill;
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

@CrossOrigin
@RestController
@RequestMapping("/softSkill")
public class SoftSkillController {

    @Autowired
    SSoftSkill sSoftSkill;
    @Autowired
    ImplementUserService implementUserService;

    @GetMapping("/listAll")
    public ResponseEntity<?> list() {
        if (sSoftSkill.list().isEmpty()) {
            return new ResponseEntity<Message>(new Message("No existen soft skills en la base de datos"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<SoftSkill>>(sSoftSkill.list(), HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> list(@PathVariable("id") String username) {
        if (!implementUserService.existsByUsername(username)) {
            return new ResponseEntity<Message>(new Message("No se encuentra el usuario ingresado"), HttpStatus.NOT_FOUND);
        }
        if (sSoftSkill.findAllByUsersUsername(username).isEmpty()) {
            return new ResponseEntity<Message>(new Message("El usuario no posee items registrados"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<SoftSkill>>(sSoftSkill.findAllByUsersUsername(username), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/add/{ss_id}/{user_id}")
    public ResponseEntity<Message> add(@PathVariable("ss_id") int ssId, @PathVariable("user_id") String username) {
        if (!sSoftSkill.existsById(ssId)) {
            return new ResponseEntity<Message>(new Message("Verifique el item o cree uno nuevo"), HttpStatus.NOT_FOUND);
        }
        if (!implementUserService.existsByUsername(username)) {
            return new ResponseEntity<Message>(new Message("No se encuentra el usuario ingresado"), HttpStatus.NOT_FOUND);
        }

        User user = implementUserService.findUser(username);
        user.addSoftSkill(sSoftSkill.findById(ssId));
        implementUserService.saveUser(user);
        return new ResponseEntity<Message>(new Message("Soft Skill agregado"), HttpStatus.OK);

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/remove/{ss_id}/{user_id}")
    public ResponseEntity<Message> remove(@PathVariable("ss_id") int ssId, @PathVariable("user_id") String username) {
        if (!sSoftSkill.existsById(ssId)) {
            return new ResponseEntity<Message>(new Message("Verifique el item o cree uno nuevo"), HttpStatus.NOT_FOUND);
        }
        if (!implementUserService.existsByUsername(username)) {
            return new ResponseEntity<Message>(new Message("No se encuentra el usuario ingresado"), HttpStatus.NOT_FOUND);
        }
        User user = implementUserService.findUser(username);
        user.removeSoftSkill(ssId);
        implementUserService.saveUser(user);
        return new ResponseEntity<Message>(new Message("Soft Skill removido"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> delete(@PathVariable("id") int id) {
        if (!sSoftSkill.existsById(id)) {
            return new ResponseEntity<Message>(new Message("No existe"), HttpStatus.NOT_FOUND);
        }
        sSoftSkill.delete(id);
        return new ResponseEntity<Message>(new Message("Soft skill eliminado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create/{user_id}")
    public ResponseEntity<Message> create(@PathVariable("user_id") String username, @RequestBody DtoSoftSkill dtoSoftSkill) {
        if (StringUtils.isBlank(dtoSoftSkill.getName()) || StringUtils.isBlank(dtoSoftSkill.getDescription())) {
            return new ResponseEntity<Message>(new Message("No se admiten campos en blanco"), HttpStatus.BAD_REQUEST);
        }
        if (!implementUserService.existsByUsername(username)) {
            return new ResponseEntity<Message>(new Message("No se pudo encontrar el usuario vinculado"), HttpStatus.BAD_REQUEST);
        }
        List<String> ss_name = sSoftSkill.findAllByUsersUsername(username).stream().map(hs -> hs.getName()).collect(Collectors.toList());
        if (ss_name.contains(dtoSoftSkill.getName())) {
            return new ResponseEntity<Message>(new Message("El usuario ya posee este item"), HttpStatus.BAD_REQUEST);
        }
        User user = implementUserService.findUser(username);

        SoftSkill softSk = new SoftSkill(dtoSoftSkill.getName(), dtoSoftSkill.getDescription());
        user.addSoftSkill(softSk);
        sSoftSkill.save(softSk);

        return new ResponseEntity<Message>(new Message("Soft Skill creado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Message> update(@PathVariable("id") int id, @RequestBody DtoSoftSkill dtoSoftSkill) {
        if (!sSoftSkill.existsById(id)) {
            return new ResponseEntity<Message>(new Message("No se encuentra  la Soft Skill que desea actualizar"), HttpStatus.NOT_FOUND);
        }
        if (StringUtils.isBlank(dtoSoftSkill.getName()) || StringUtils.isBlank(dtoSoftSkill.getDescription())) {
            return new ResponseEntity<Message>(new Message("No se aceptan campos vacíos"), HttpStatus.BAD_REQUEST);
        }
        SoftSkill softSkill = sSoftSkill.findById(id);
        softSkill.setName(dtoSoftSkill.getName());
        softSkill.setDescription(dtoSoftSkill.getDescription());
        sSoftSkill.save(softSkill);
        return new ResponseEntity<Message>(new Message("Soft Skill actualizado"), HttpStatus.OK);
    }

}
