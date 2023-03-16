package com.portfolio.Controller;

import com.portfolio.Dto.DtoMail;
import com.portfolio.Security.Controller.Message;
import com.portfolio.Service.SMail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class MailController {

    @Autowired
    SMail sMailer;

    @PostMapping("/sendMail")
    public ResponseEntity<Message> sendingMail(@RequestBody DtoMail dtoMail) {

        if (StringUtils.isBlank(dtoMail.getMail()) || StringUtils.isBlank(dtoMail.getMessage()) || StringUtils.isBlank(dtoMail.getName())) {
            return new ResponseEntity<Message>(new Message("Revise el campo en blanco"), HttpStatus.BAD_REQUEST);
        }
        try {
            String message = "Nombre: " + dtoMail.getName() + "\n\n Mail: " + dtoMail.getMail() + "\n\n Mensaje: " + dtoMail.getMessage();
            sMailer.sendMail(dtoMail.getMail(), message);
            return new ResponseEntity<Message>(new Message("Mensaje enviado con éxito"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Message>(new Message("Ocurrió un problema, por favor intente más tarde. Error: " + e), HttpStatus.BAD_REQUEST);
        }
    }

}
