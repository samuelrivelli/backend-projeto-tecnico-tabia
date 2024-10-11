package com.tabia.projeto_tecnico.service;

import com.tabia.projeto_tecnico.exceptions.UserNotFoundException;
import com.tabia.projeto_tecnico.model.dto.UserDTO;
import com.tabia.projeto_tecnico.model.entity.UserEntity;
import com.tabia.projeto_tecnico.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService  {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findAll(){
        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> findById(@NotNull Long id){

        if (id == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }

        Optional<UserEntity> userEntity = userRepository.findById(id);

        if(!userEntity.isPresent()){
            throw new UserNotFoundException("User not found!");
        }

        return Optional.of(convertToDTO(userEntity.get()));
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

}
