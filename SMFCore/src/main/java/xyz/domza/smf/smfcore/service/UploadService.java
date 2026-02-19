package xyz.domza.smf.smfcore.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.domza.smf.smfcore.dto.UserDataDTO;
import xyz.domza.smf.smfcore.exception.StorageException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UploadService {
    private UserService userService;
    private StorageService storageService;

    public UploadService(UserService userService, StorageService storageService) {
        this.userService = userService;
        this.storageService = storageService;
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
        storageService.uploadFiles(files, uploadId.toString());
        userService.addReceivedUpload(userData.get().username(), uploadId.toString(), message, files.size());
    }
}
