package xyz.domza.sendmefiles.smf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.domza.sendmefiles.smf.entity.UserInfo;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByEmail(String email);
}
