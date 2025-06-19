package xyz.domza.sendmefiles.smf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.domza.sendmefiles.smf.entity.UploadInfo;

public interface UploadInfoRepository extends JpaRepository<UploadInfo, Integer> {
}
