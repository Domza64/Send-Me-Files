package xyz.domza.sendmefiles.smf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.domza.sendmefiles.smf.entity.UploadInfo;
import java.util.List;

public interface UploadInfoRepository extends JpaRepository<UploadInfo, Integer> {
    List<UploadInfo> findByUserEmail(String email);
}
