package com.example.proiect1.Controller;

import com.example.proiect1.dto.UserDTO;
import com.example.proiect1.dto.UserRegisterDTO;
import com.example.proiect1.dto.UserLoginDTO;
import com.example.proiect1.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO userDTO) {
        userService.register(userDTO);
        return ResponseEntity.ok(Map.of("message", "User registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginDTO) {
        return new ResponseEntity<>(userService.login(loginDTO), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500") // sau "*", pentru test
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody com.example.proiect1.dto.UserUpdateDTO dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
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
