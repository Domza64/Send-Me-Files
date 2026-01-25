package xyz.domza.sendmefiles.smf.controller;

import org.springframework.web.bind.annotation.*;
import xyz.domza.sendmefiles.smf.entity.UserInfo;
import xyz.domza.sendmefiles.smf.service.UserService;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserInfo> getAllUsers(Principal principal) {
        return userService.getAllUsers();
    }
}
