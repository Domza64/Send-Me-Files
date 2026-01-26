package xyz.domza.sendmefiles.smf.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.domza.sendmefiles.smf.dto.UploadInfoDTO;
import xyz.domza.sendmefiles.smf.dto.UserDataDTO;
import xyz.domza.sendmefiles.smf.service.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserDataController {
    UserService userService;

    public UserDataController(UserService userInfoService) {
        this.userService = userInfoService;
    }

    @GetMapping("/data")
    public ResponseEntity<UserDataDTO> getUserData(Principal principal) {
        Optional<UserDataDTO> userData = userService.getUserDataByEmail(principal.getName());
        return userData.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/received")
    public ResponseEntity<List<UploadInfoDTO>> getReceivedUploads(Principal principal) {
        String email = principal.getName();
        List<UploadInfoDTO> receivedUploads = userService.getUploads(email);
        return ResponseEntity.ok(receivedUploads);
    }

    // This can be cashed to prevent unnecesarry list bucket calls since data shouldn't be changed after initial upload anyway
    @GetMapping("/received/{uploadId}")
    public ResponseEntity<List<String>> getUploadContents(Principal principal, @PathVariable String uploadId) {
        // TODO: Some more descriptive object for files instead of just name?
        return ResponseEntity.ok(
                userService.getFilesInUpload(uploadId, principal.getName())
        );
    }
}
