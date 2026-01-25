package xyz.domza.sendmefiles.smf.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import xyz.domza.sendmefiles.smf.dto.AuthRequestDTO;
import xyz.domza.sendmefiles.smf.entity.UserInfo;
import xyz.domza.sendmefiles.smf.security.service.JwtService;
import xyz.domza.sendmefiles.smf.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserService service;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    public AuthController(UserService service, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // FOR TESTING PURPOSES ONLY, FOR NOW...
    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequestDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.email());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
