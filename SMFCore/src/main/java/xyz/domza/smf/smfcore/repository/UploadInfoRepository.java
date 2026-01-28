package xyz.domza.smf.smfcore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.domza.smf.smfcore.entity.UploadInfo;

import java.util.List;
import java.util.Optional;

public interface UploadInfoRepository extends JpaRepository<UploadInfo, Integer> {
    List<UploadInfo> findByUserEmail(String email);
    Optional<UploadInfo> findByUploadIdAndUser_Email(String uploadId, String email);
}
