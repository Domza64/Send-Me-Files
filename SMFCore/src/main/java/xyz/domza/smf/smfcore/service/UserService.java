package xyz.domza.smf.smfcore.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.domza.smf.smfcore.dto.UploadInfoDTO;
import xyz.domza.smf.smfcore.dto.UserDataDTO;
import xyz.domza.smf.smfcore.entity.UploadInfo;
import xyz.domza.smf.smfcore.entity.UserInfo;
import xyz.domza.smf.smfcore.exception.NotFoundException;
import xyz.domza.smf.smfcore.repository.UploadInfoRepository;
import xyz.domza.smf.smfcore.repository.UserInfoRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private PasswordEncoder encoder;
    private UserInfoRepository userInfoRepository;
    private UploadInfoRepository uploadInfoRepository;
    private StorageService storageService;

    public UserService(PasswordEncoder encoder, UserInfoRepository userInfoRepository, UploadInfoRepository uploadInfoRepository, StorageService storageService) {
        this.encoder = encoder;
        this.userInfoRepository = userInfoRepository;
        this.uploadInfoRepository = uploadInfoRepository;
        this.storageService = storageService;
    }

    public String addUser(UserInfo userInfo) {
        // Encode password before saving the user
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "User Added Successfully";
    }

    // TODO - Return list of DTOs to exclude passwords
    public List<UserInfo> getAllUsers() {
        return userInfoRepository.findAll();
    }

    public Optional<UserDataDTO> getUserDataByEmail(String email) {
        return getUserData(userInfoRepository.findByEmail(email));
    }

    public Optional<UserDataDTO> getUserDataByUsername(String username) {
        return getUserData(userInfoRepository.findByUsername(username));
    }

    /**
     * Converts an Optional<UserInfo> to Optional<UserDataDTO>
     */
    private Optional<UserDataDTO> getUserData(Optional<UserInfo> userInfoOptional) {
        return userInfoOptional.map(userInfo -> new UserDataDTO(
                userInfo.getUsername(),
                userInfo.getUploads().stream()
                        .map(UploadInfoDTO::convertToDTO)
                        .toList()
        ));
    }

    public void addReceivedUpload(String username, String uploadId, String message, int fileCount) throws UsernameNotFoundException {
        Optional<UserInfo> userOptional = userInfoRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            UserInfo user = userOptional.get();
            UploadInfo upload = new UploadInfo(uploadId, message, new Date(), user, fileCount);
            user.getUploads().add(upload);
            userInfoRepository.save(user);
        }
        else {
            throw new UsernameNotFoundException("User " + username + " not found.");
        }
    }

    public List<UploadInfoDTO> getUploads(String email) {
        return uploadInfoRepository.findByUserEmail(email).stream().map(UploadInfoDTO::convertToDTO).toList();
    }

    // returns list of strings, each string is file name for each file
    public List<String> getFilesInUpload(String uploadId, String email) { // Email from user that received files and should have read permission
        Optional<UploadInfo> optional = uploadInfoRepository.findByUploadIdAndUser_Email(uploadId, email);
        if (optional.isEmpty()) {
            throw new NotFoundException("Upload " + uploadId + " not found."); // why returns 403 instead of 404
        }

        return storageService.listObjects(uploadId);
    }
}
