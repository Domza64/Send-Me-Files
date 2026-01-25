package xyz.domza.sendmefiles.smf.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.domza.sendmefiles.smf.dto.UserDataDTO;
import xyz.domza.sendmefiles.smf.exception.StorageException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UploadService {
    private UserService userService;
    private CloudflareService cloudflareService;

    public UploadService(UserService userService, CloudflareService cloudflareService) {
        this.userService = userService;
        this.cloudflareService = cloudflareService;
    }

    // TODO: Support requestUploadId
    public void uploadFiles(List<MultipartFile> files, String recipientUsername, String message) throws StorageException, UsernameNotFoundException {
        Optional<UserDataDTO> userData = userService.getUserDataByUsername(recipientUsername);
        if (userData.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        // TESTING PURPOSES, if file is .txt throw Exception and aboard upload
        files.forEach(file -> {
            if (Objects.requireNonNull(file.getOriginalFilename()).endsWith(".txt")) {
                throw new StorageException("StorageException thrown to test ExceptionHandling - upload a non txt file for success.");
            }
        });

        UUID uploadId = UUID.randomUUID();
        cloudflareService.uploadFiles(files, uploadId.toString());
        userService.addReceivedUpload(userData.get().username(), uploadId.toString(), message, files.size());
    }
}
