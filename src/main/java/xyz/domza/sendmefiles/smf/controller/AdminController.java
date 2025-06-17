package xyz.domza.sendmefiles.smf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.domza.sendmefiles.smf.entity.UserInfo;
import xyz.domza.sendmefiles.smf.service.UserInfoService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserInfo> getAllUsers() {
        return userInfoService.getAllUsers();
    }
}
