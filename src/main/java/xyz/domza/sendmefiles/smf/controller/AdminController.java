package xyz.domza.sendmefiles.smf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.domza.sendmefiles.smf.entity.UserInfo;
import xyz.domza.sendmefiles.smf.service.UserInfoService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/users")
    public List<UserInfo> getAllUsers(Principal principal) {
        return userInfoService.getAllUsers();
    }
}
