package xyz.domza.sendmefiles.smf.dto;

import xyz.domza.sendmefiles.smf.entity.UploadInfo;

public record UploadInfoDTO(String uploadId, String title) {
    public static UploadInfoDTO convertToDTO(UploadInfo upload) {
        return new UploadInfoDTO(upload.getUploadId(), upload.getTitle());
    }
}
