package com.portfolio.Security.Controller;

import com.portfolio.Security.Dto.JwtDto;
import com.portfolio.Security.Dto.LoginUser;
import com.portfolio.Security.Dto.NewUser;
import com.portfolio.Security.Entity.PersonUser;
import com.portfolio.Security.Entity.Rol;
import com.portfolio.Security.Enums.RolName;
import com.portfolio.Security.Service.RolService;
import com.portfolio.Security.Service.UserService;
import com.portfolio.Security.jwt.JwtProvider;
import com.portfolio.Service.ImplementUserService;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    RolService rolService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    ImplementUserService implementUserService;

    @PostMapping("/generated")
    public ResponseEntity<?> generated(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Message>(new Message("Revise los campos ingresados o email invalido"), HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByUsername(newUser.getUsername())) {
            return new ResponseEntity<Message>(new Message("Ya existe el identificador ingresado"), HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByEmail(newUser.getEmail())) {
            return new ResponseEntity<Message>(new Message("Ye existe el email ingresado"), HttpStatus.BAD_REQUEST);
        }

        try {
            PersonUser personUser = new PersonUser(newUser.getName(), newUser.getUsername(), newUser.getEmail(), passwordEncoder.encode(newUser.getPassword()));

            Set<Rol> rols = new HashSet<>();
            rols.add(rolService.getByRolName(RolName.ROLE_USER).get());

            if (newUser.getRols().contains("admin")) {
                rols.add(rolService.getByRolName(RolName.ROLE_ADMIN).get());
            }
            personUser.setRols(rols);
            userService.save(personUser);
            return new ResponseEntity<Message>(new Message("Usuario guardado"), HttpStatus.CREATED);

        } catch (Exception e) {
            System.out.println("Aqui se ve el error: " + e);
            return new ResponseEntity<Message>(new Message("Usuario NO guardado" + e), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Message>(new Message("Comprobar los campos ingresados"), HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean exist = implementUserService.existsByUsername(loginUser.getUsername());
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities(), exist);

        return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.OK);

    }
}
