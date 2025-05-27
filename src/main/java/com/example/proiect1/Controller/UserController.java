package com.example.proiect1.Controller;

import com.example.proiect1.dto.UserDTO;
import com.example.proiect1.dto.UserRegisterDTO;
import com.example.proiect1.dto.UserLoginDTO;
import com.example.proiect1.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://192.168.100.3:5500")
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
        UserDTO user = userService.login(loginDTO);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/test-admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Admin access OK");
    }

    @GetMapping("/test-client")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<String> clientOnly() {
        return ResponseEntity.ok("Client access OK");
    }
}
