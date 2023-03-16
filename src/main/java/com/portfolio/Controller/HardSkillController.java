package com.portfolio.Controller;

import com.portfolio.Dto.DtoHardSkills;
import com.portfolio.Entity.HardSkill;
import com.portfolio.Entity.User;
import com.portfolio.Security.Controller.Message;
import com.portfolio.Service.ImplementUserService;
import com.portfolio.Service.SHardSkill;
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

@CrossOrigin(origins = {"https://heberportfolio.web.app"})
@RestController
@RequestMapping("/hardSkill")
public class HardSkillController {

    @Autowired
    SHardSkill sHardSkill;

    @Autowired
    ImplementUserService implementUserService;

    @GetMapping("/listAll")
    public ResponseEntity<?> list() {
        if (sHardSkill.list().isEmpty()) {
            return new ResponseEntity<Message>(new Message("No existen hard skills en la base de datos"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<HardSkill>>(sHardSkill.list(), HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> list(@PathVariable("id") String username) {
        if (!implementUserService.existsByUsername(username)) {
            return new ResponseEntity<Message>(new Message("No se encuentra el usuario ingresado"), HttpStatus.NOT_FOUND);
        }
        if (sHardSkill.findAllByUsersUsername(username).isEmpty()) {
            return new ResponseEntity<Message>(new Message("El usuario no tiene skills registradas"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<HardSkill>>(sHardSkill.findAllByUsersUsername(username), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/add/{hs_id}/{user_id}")
    public ResponseEntity<Message> add(@PathVariable("hs_id") int hsId, @PathVariable("user_id") String username) {
        if (!sHardSkill.existsById(hsId)) {
            return new ResponseEntity<Message>(new Message("Verifique el item o cree uno nuevo"), HttpStatus.NOT_FOUND);
        }
        if (!implementUserService.existsByUsername(username)) {
            return new ResponseEntity<Message>(new Message("No se encuentra el usuario ingresado"), HttpStatus.NOT_FOUND);
        }
        User user = implementUserService.findUser(username);
        user.addHardSkill(sHardSkill.findById(hsId));
        implementUserService.saveUser(user);
        return new ResponseEntity<Message>(new Message("Hard Skill agregado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/remove/{hs_id}/{user_id}")
    public ResponseEntity<Message> remove(@PathVariable("hs_id") int hsId, @PathVariable("user_id") String username) {
        if (!sHardSkill.existsById(hsId)) {
            return new ResponseEntity<Message>(new Message("Verifique el item o cree uno nuevo"), HttpStatus.NOT_FOUND);
        }
        if (!implementUserService.existsByUsername(username)) {
            return new ResponseEntity<Message>(new Message("No se encuentra el usuario ingresado"), HttpStatus.NOT_FOUND);
        }
        User user = implementUserService.findUser(username);
        user.removeHardSkill(hsId);
        implementUserService.saveUser(user);

        return new ResponseEntity<Message>(new Message("Hard Skill removido"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> delete(@PathVariable("id") int id) {
        if (!sHardSkill.existsById(id)) {
            return new ResponseEntity<Message>(new Message("No se encuentra el skill que desea  eliminar"), HttpStatus.NOT_FOUND);
        }
        sHardSkill.delete(id);
        return new ResponseEntity<Message>(new Message("Hard Skill eliminado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create/{user_id}")
    public ResponseEntity<Message> create(@PathVariable("user_id") String username, @RequestBody DtoHardSkills dtoHardSkill) {
        if (StringUtils.isBlank(dtoHardSkill.getName())) {
            return new ResponseEntity<Message>(new Message("Revise el campo en blanco"), HttpStatus.BAD_REQUEST);
        }
        if ((dtoHardSkill.getPercentage() % 5 != 0) || (dtoHardSkill.getPercentage() > 100)) {
            return new ResponseEntity<Message>(new Message("No se acepta el porcentage inresado, solo multiplos de 5"), HttpStatus.BAD_REQUEST);

        }
        if (!implementUserService.existsByUsername(username)) {
            return new ResponseEntity<Message>(new Message("No se pudo encontrar el usuario vinculado"), HttpStatus.BAD_REQUEST);
        }
        List<String> hss_name = sHardSkill.findAllByUsersUsername(username).stream().map(hs -> hs.getName()).collect(Collectors.toList());
        if (hss_name.contains(dtoHardSkill.getName())) {
            return new ResponseEntity<Message>(new Message("El usuario ya posee este item"), HttpStatus.BAD_REQUEST);
        }
        User user = implementUserService.findUser(username);
        List<HardSkill> hss = sHardSkill.findAllByName(dtoHardSkill.getName()).stream()
                .filter(hs -> hs.getPercentage() == dtoHardSkill.getPercentage()).collect(Collectors.toList());
        if (!hss.isEmpty()) {
            HardSkill hardS = hss.get(0);
            user.addHardSkill(hardS);
            implementUserService.saveUser(user);
            return new ResponseEntity<Message>(new Message("Hard Skill creado"), HttpStatus.OK);
        }

        HardSkill hardSk = new HardSkill(dtoHardSkill.getName(), dtoHardSkill.getPercentage(), dtoHardSkill.getImg(), dtoHardSkill.getType_img());
        user.addHardSkill(hardSk);
        sHardSkill.save(hardSk);
        return new ResponseEntity<Message>(new Message("Hard Skill creado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Message> update(@PathVariable("id") int id, @RequestBody DtoHardSkills dtoHardSkill) {
        if (!sHardSkill.existsById(id)) {
            return new ResponseEntity<Message>(new Message("No se encuentra el skill que desea actualizar"), HttpStatus.NOT_FOUND);
        }
        if (StringUtils.isBlank(dtoHardSkill.getName())) {
            return new ResponseEntity<Message>(new Message("No se aceptan campos vacíos"), HttpStatus.BAD_REQUEST);
        }
        if ((dtoHardSkill.getPercentage() % 5 != 0) || (dtoHardSkill.getPercentage() > 100)) {
            return new ResponseEntity<Message>(new Message("No se acepta el porcentage inresado"), HttpStatus.BAD_REQUEST);
        }
        List<HardSkill> hss = sHardSkill.findAllByName(dtoHardSkill.getName()).stream()
                .filter(hs -> hs.getPercentage() == dtoHardSkill.getPercentage()).collect(Collectors.toList());
        if (!hss.isEmpty()) {
            return new ResponseEntity<Message>(new Message("Ya existe este item"), HttpStatus.BAD_REQUEST);
        }

        HardSkill hardSkill = sHardSkill.findById(id);
        hardSkill.setName(dtoHardSkill.getName());
        hardSkill.setPercentage(dtoHardSkill.getPercentage());
        hardSkill.setImg(dtoHardSkill.getImg());
        hardSkill.setType_img(dtoHardSkill.getType_img());
        sHardSkill.save(hardSkill);
        return new ResponseEntity<Message>(new Message("Hard Skill actualizado"), HttpStatus.OK);
    }

}
