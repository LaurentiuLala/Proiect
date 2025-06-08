package com.example.proiect1.Controller;

import com.example.proiect1.Models.User;
import com.example.proiect1.Repo.UserRepo;
import com.example.proiect1.dto.UserDTO;
import com.example.proiect1.dto.UserRegisterDTO;
import com.example.proiect1.dto.UserLoginDTO;
import com.example.proiect1.service.InchiriereService;
import com.example.proiect1.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class UserController {
    @Autowired
    private final UserService userService;
    private final UserRepo userRepo;
    private InchiriereService inchiriereService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO userDTO) {
        userService.register(userDTO);
        return ResponseEntity.ok(Map.of("message", "User registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginDTO) {
        return new ResponseEntity<>(userService.login(loginDTO), HttpStatus.OK);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/account/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<UserDTO> getAccountById(@PathVariable Long id) {
        UserDTO dto = userService.getUserById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody com.example.proiect1.dto.UserUpdateDTO dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(Map.of("message", "User deleted"));
    }


    @GetMapping("/test-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Admin access OK");
    }

    @GetMapping("/test-client")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> clientOnly() {
        return ResponseEntity.ok("Client access OK");
    }
}
