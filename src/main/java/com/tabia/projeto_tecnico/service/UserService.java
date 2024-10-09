package com.tabia.projeto_tecnico.service;

import com.tabia.projeto_tecnico.exceptions.InvalidPasswordException;
import com.tabia.projeto_tecnico.exceptions.UserNotFoundException;
import com.tabia.projeto_tecnico.model.dto.UserDTO;
import com.tabia.projeto_tecnico.model.entity.UserEntity;
import com.tabia.projeto_tecnico.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public List<UserDTO> findAll(){
        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> findById(UUID id){

        if (id == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }

        Optional<UserEntity> userEntity = userRepository.findById(id);

        if(!userEntity.isPresent()){
            throw new UserNotFoundException("User not found!");
        }

        return Optional.of(convertToDTO(userEntity.get()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        String[] roles = userEntity.isAdmin()
                ? new String[]{"ADMIN","USER"}
                : new String[]{"USER"};

        return User
                .builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles(roles)
                .build();
    }

    @Transactional
    public UserEntity save(UserDTO userDTO) {
        if (userDTO.getPassword() == null || userDTO.getPassword().trim().equals("")) {
            throw new InvalidPasswordException("Invalid password");
        }
        UserEntity userEntity = create(userDTO);
        validate(userEntity);

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userEntity.setPassword(encodedPassword);
        userRepository.save(userEntity);
        return userEntity;

    }


    public UserDTO convertToDTO(UserEntity user) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    public UserEntity create(UserDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        return userEntity;
    }

    public void validate(UserEntity userEntity){
        if(userEntity.getUsername() == null
                || userEntity.getUsername().trim().equals("" )
                || userEntity.getFirstName() == null
                || userEntity.getFirstName().trim().equals("")
                || userEntity.getLastName() == null
                || userEntity.getLastName().trim().equals("")
        ){
            throw new RuntimeException("Invalid login");
        }
    }
}
