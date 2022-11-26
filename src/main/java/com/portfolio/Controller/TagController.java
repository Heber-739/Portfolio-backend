package com.portfolio.Controller;

import com.portfolio.Dto.DtoTag;
import com.portfolio.Entity.Education;
import com.portfolio.Entity.Tag;
import com.portfolio.Security.Controller.Message;
import com.portfolio.Service.ImplementUserService;
import com.portfolio.Service.SEducation;
import com.portfolio.Service.STag;
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
@RequestMapping("/tags")
public class TagController {

    @Autowired
    STag sTag;
    @Autowired
    SEducation sEducation;
    @Autowired
    ImplementUserService implementUserService;

    //Los usuarios podrán listar y elegir los tags para agregar a sus educaciones
    @GetMapping("/listAll")
    public ResponseEntity<?> list() {
        if (sTag.list().isEmpty()) {
            return new ResponseEntity<Message>(new Message("No existen items en la base de datos"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Tag>>(sTag.list(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list/{education_id}")
    public ResponseEntity<?> listAll(@PathVariable("education_id") int id) {
        if (!sEducation.existsById(id)) {
            return new ResponseEntity<Message>(new Message("No se encuentra la educación vinculada a los contenidos"), HttpStatus.NOT_FOUND);
        }
        if (sTag.findAllByEducationsId(id).isEmpty()) {
            return new ResponseEntity<Message>(new Message("La educación aún no posee contenidos"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Tag>>(sTag.findAllByEducationsId(id), HttpStatus.OK);
    }

    //Los usuarios podrán listar y elegir los tags para agregar a sus educaciones
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/add/{tag_id}/{ed_id}")
    public ResponseEntity<Message> add(@PathVariable("tag_id") int id, @PathVariable("ed_id") int ed_id) {

        if (!sTag.existsById(id)) {
            return new ResponseEntity<Message>(new Message("No se encuentra el contenido que desea vincular"), HttpStatus.NOT_FOUND);
        }

        if (!sEducation.existsById(ed_id)) {
            return new ResponseEntity<Message>(new Message("No se encuentra la educaación en el registro"), HttpStatus.NOT_FOUND);
        }
        Education ed = sEducation.findById(ed_id);
        ed.addTag(sTag.findById(id));
        sEducation.save(ed);
        return new ResponseEntity<Message>(new Message("Contenido agregado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/remove/{tag_id}/{ed_id}")
    public ResponseEntity<Message> remove(@PathVariable("tag_id") int id, @PathVariable("ed_id") int ed_id) {
        if (!sTag.existsById(id)) {
            return new ResponseEntity<Message>(new Message("No se encuentra el contenido que desea eliminar"), HttpStatus.NOT_FOUND);
        }
        if (!sEducation.existsById(ed_id)) {
            return new ResponseEntity<Message>(new Message("No se encuentra la educaación en vinculada el registro"), HttpStatus.NOT_FOUND);
        }

        Education ed = sEducation.findById(ed_id);
        ed.removeTag(id);
        sEducation.save(ed);
        return new ResponseEntity<Message>(new Message("Contenido removido"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> delete(@PathVariable("id") int id) {
        if (!sTag.existsById(id)) {
            return new ResponseEntity<Message>(new Message("No existe el contenido"), HttpStatus.NOT_FOUND);
        }
        sTag.delete(id);
        return new ResponseEntity<Message>(new Message("Contenido eliminado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<Message> create(@RequestBody DtoTag dtoTag) {
        if (StringUtils.isBlank(dtoTag.getAbbreviation()) || StringUtils.isBlank(dtoTag.getName())) {
            return new ResponseEntity<Message>(new Message("Revise el campo en blanco"), HttpStatus.BAD_REQUEST);
        }
        if (!sEducation.existsById(dtoTag.getEducationId())) {
            return new ResponseEntity<Message>(new Message("No se encuentra la educación vinculada en el registro"), HttpStatus.NOT_FOUND);
        }
        if (sTag.existsByName(dtoTag.getName())) {
            return new ResponseEntity<Message>(new Message("Ya existe el contenido"), HttpStatus.BAD_REQUEST);
        }
        List<String> tags = sEducation.findById(dtoTag.getEducationId()).getTags().stream().map(t -> t.getName()).collect(Collectors.toList());
        if (tags.contains(dtoTag.getName())) {
            return new ResponseEntity<Message>(new Message("La educación ya posee este contenido"), HttpStatus.BAD_REQUEST);
        }
        Tag tag = new Tag(dtoTag.getAbbreviation(), dtoTag.getName());
        Education ed = sEducation.findById(dtoTag.getEducationId());
        ed.addTag(tag);
        sTag.save(tag);
        return new ResponseEntity<Message>(new Message("Item creado correctamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Message> update(@PathVariable("id") int id, @RequestBody DtoTag dtoTag) {
        if (!sTag.existsById(id)) {
            return new ResponseEntity<Message>(new Message("No se encuentra el item"), HttpStatus.NOT_FOUND);
        }
        if (StringUtils.isBlank(dtoTag.getAbbreviation()) || StringUtils.isBlank(dtoTag.getName())) {
            return new ResponseEntity<Message>(new Message("Revise el campo en blanco"), HttpStatus.BAD_REQUEST);
        }
        if (sTag.existsByName(dtoTag.getName()) && sTag.findByName(dtoTag.getName()).get().getId() != id) {
            return new ResponseEntity<Message>(new Message("Ya existe el item"), HttpStatus.BAD_REQUEST);
        }
        Tag tag = sTag.findById(id);
        tag.setAbbreviation(dtoTag.getAbbreviation());
        tag.setName(dtoTag.getName());
        sTag.save(tag);
        return new ResponseEntity<Message>(new Message("Item actualizado"), HttpStatus.OK);

    }

}
