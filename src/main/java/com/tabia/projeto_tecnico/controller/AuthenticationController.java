package com.tabia.projeto_tecnico.controller;


import com.tabia.projeto_tecnico.enums.UserRole;
import com.tabia.projeto_tecnico.model.dto.AuthenticationDTO;
import com.tabia.projeto_tecnico.model.dto.LoginResponseDTO;
import com.tabia.projeto_tecnico.model.dto.RegisterDTO;
import com.tabia.projeto_tecnico.model.entity.UserEntity;
import com.tabia.projeto_tecnico.repository.UserRepository;
import com.tabia.projeto_tecnico.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var user = (UserEntity) auth.getPrincipal();
        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDTO(token, user.getId(), user.getRole()));
    }


    @PostMapping("/register/user")
    public ResponseEntity<UserEntity> registerUser(@RequestBody @Valid RegisterDTO data) {
        if (this.repository.findByUsername(data.username()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        UserRole userRole = UserRole.USER;

        UserEntity newUser = new UserEntity(data.username(), encryptedPassword, userRole);

        this.repository.save(newUser);

        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<UserEntity> registerAdmin(@RequestBody @Valid RegisterDTO data) {

        if (this.repository.findByUsername(data.username()) != null) {
            return ResponseEntity.badRequest().build();
        }

        UserRole userRole = UserRole.ADMIN;

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserEntity newAdmin = new UserEntity(data.username(), encryptedPassword, userRole);

        this.repository.save(newAdmin);

        return ResponseEntity.ok(newAdmin);
    }


}