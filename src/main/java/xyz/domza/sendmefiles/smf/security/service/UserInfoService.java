package xyz.domza.sendmefiles.smf.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.domza.sendmefiles.smf.entity.UserInfo;
import xyz.domza.sendmefiles.smf.repository.UserInfoRepository;
import xyz.domza.sendmefiles.smf.security.UserInfoDetails;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
    private final UserInfoRepository repository;

    public UserInfoService(UserInfoRepository repository) {
        this.repository = repository;
    }

    /**
     * Loads a user for authentication by email address.
     * (named "loadUserByUsername" due to Spring Security API constraints)
     * <p>
     * Although the method name and parameter are defined as {@code loadUserByUsername}
     * by Spring Security's {@link UserDetailsService} contract, this application
     * uses the user's <strong>email address</strong> as the unique login identifier.
     * <p>
     * Internally, the provided {@code username} parameter is therefore treated as
     * an email and used to look up the user via {@code findByEmail}.
     *
     * @param email the user's email address
     * @return a fully populated {@link UserDetails} instance
     * @throws UsernameNotFoundException if no user is found for the given email
     */
    @Override
    public UserInfoDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByEmail(email);

        // Converting UserInfo to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}