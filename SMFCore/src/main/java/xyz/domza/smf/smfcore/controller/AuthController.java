package xyz.domza.smf.smfcore.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import xyz.domza.smf.smfcore.dto.AuthRequestDTO;
import xyz.domza.smf.smfcore.dto.UserInfoDTO;
import xyz.domza.smf.smfcore.entity.UserInfo;
import xyz.domza.smf.smfcore.security.service.JwtService;
import xyz.domza.smf.smfcore.service.UserService;

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
    public String addNewUser(@RequestBody UserInfoDTO userInfoDTO) {
        return service.addUser(new UserInfo(userInfoDTO.username(), userInfoDTO.email(), userInfoDTO.password(), userInfoDTO.role()));
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
