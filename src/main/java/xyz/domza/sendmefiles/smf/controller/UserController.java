package xyz.domza.sendmefiles.smf.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.domza.sendmefiles.smf.dto.UserDataDTO;
import xyz.domza.sendmefiles.smf.service.UserService;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserService userService;

    public UserController(UserService userInfoService) {
        this.userService = userInfoService;
    }

    @GetMapping("/userData")
    public ResponseEntity<UserDataDTO> getUserData(Principal principal) {
        Optional<UserDataDTO> userData = userService.getUserData(principal.getName());
        return userData.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
