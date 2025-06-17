package xyz.domza.sendmefiles.smf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.domza.sendmefiles.smf.dto.UserDataDTO;
import xyz.domza.sendmefiles.smf.service.UserInfoService;
import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/userData")
    @PreAuthorize("hasAuthority('USER')")
    public UserDataDTO getAllUsers(Principal principal) {
        System.out.println(principal);
        System.out.println(principal.getName());
        return userInfoService.getUserData(principal.getName());
    }
}
