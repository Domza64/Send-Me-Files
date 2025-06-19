package xyz.domza.sendmefiles.smf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.domza.sendmefiles.smf.dto.UploadInfoDTO;
import xyz.domza.sendmefiles.smf.dto.UserDataDTO;
import xyz.domza.sendmefiles.smf.entity.UploadInfo;
import xyz.domza.sendmefiles.smf.entity.UserInfo;
import xyz.domza.sendmefiles.smf.repository.UserInfoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByUsername(username);

        // Converting UserInfo to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public String addUser(UserInfo userInfo) {
        // Encode password before saving the user
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }

    // TODO - Return list of DTOs to exclude passwords
    public List<UserInfo> getAllUsers() {
        return repository.findAll();
    }

    public Optional<UserDataDTO> getUserData(String username) {
        Optional<UserInfo> userInfoOptional = repository.findByUsername(username);
        if (userInfoOptional.isPresent()) {
            UserInfo userInfo = userInfoOptional.get();
            return Optional.of(new UserDataDTO(userInfo.getUsername(),
                    userInfo.getUploads().stream().map(UploadInfoDTO::convertToDTO).toList()));
        }
        return Optional.empty();
    }

    public void addRecievedUpload(String username, String uploadId) throws UsernameNotFoundException {
        Optional<UserInfo> userOptional = repository.findByUsername(username);
        if (userOptional.isPresent()) {
            UserInfo user = userOptional.get();
            UploadInfo upload = new UploadInfo("Some title", uploadId, new Date(), user);
            user.getUploads().add(upload);
            repository.save(user);
        }
        else {
            throw new UsernameNotFoundException("User " + username + " not found.");
        }
    }
}