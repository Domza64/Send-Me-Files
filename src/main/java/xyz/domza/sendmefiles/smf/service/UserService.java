package xyz.domza.sendmefiles.smf.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.domza.sendmefiles.smf.dto.UploadInfoDTO;
import xyz.domza.sendmefiles.smf.dto.UserDataDTO;
import xyz.domza.sendmefiles.smf.entity.UploadInfo;
import xyz.domza.sendmefiles.smf.entity.UserInfo;
import xyz.domza.sendmefiles.smf.repository.UploadInfoRepository;
import xyz.domza.sendmefiles.smf.repository.UserInfoRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private PasswordEncoder encoder;
    private UserInfoRepository userInfoRepository;
    private UploadInfoRepository uploadInfoRepository;

    public UserService(PasswordEncoder encoder, UserInfoRepository userInfoRepository, UploadInfoRepository uploadInfoRepository) {
        this.encoder = encoder;
        this.userInfoRepository = userInfoRepository;
        this.uploadInfoRepository = uploadInfoRepository;
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
}
