package xyz.domza.smf.smfcore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.domza.smf.smfcore.dto.UploadInfoDTO;
import xyz.domza.smf.smfcore.dto.UserDataDTO;
import xyz.domza.smf.smfcore.service.UserDataService;
import xyz.domza.smf.smfcore.service.UserService;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserDataController {
    private UserService userService;
    private UserDataService userDataService;

    public UserDataController(UserService userService, UserDataService userDataService) {
        this.userService = userService;
        this.userDataService = userDataService;
    }

    // TODO: Return some meaningful info
    @GetMapping("/zip/{uploadId}")
    public ResponseEntity<String> requestZipDownload(@PathVariable String uploadId) {
        String response;
        try {
            response = userDataService.requestZipDownload(uploadId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating a zip download request. " + e.getMessage());
        }
        return ResponseEntity.ok(response);
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
