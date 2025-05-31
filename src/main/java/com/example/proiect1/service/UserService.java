package com.example.proiect1.service;

import com.example.proiect1.Models.User;
import com.example.proiect1.Repo.UserRepo;
import com.example.proiect1.dto.UserDTO;
import com.example.proiect1.dto.UserLoginDTO;
import com.example.proiect1.dto.UserRegisterDTO;
import com.example.proiect1.dto.UserUpdateDTO;
import com.example.proiect1.exception.UserAlreadyExistsException;
import com.example.proiect1.exception.UserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO register(UserRegisterDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already in use");
        }

        User user = User.builder()
                .name(dto.getName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role("CLIENT")
                .build();

        User saved = userRepository.save(user);
        return toDTO(saved);
    }

    public UserDTO login(UserLoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return toDTO(user);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound("User not found"));

        return toDTO(user);
    }

    public UserDTO updateUser(Long id, UserUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setLastName(dto.getLastName());

        User updated = userRepository.save(user);
        return toDTO(updated);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }



    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFound("User not found");
        }
        userRepository.deleteById(id);
    }


    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
