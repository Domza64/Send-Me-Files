package xyz.domza.sendmefiles.smf.dto;

import xyz.domza.sendmefiles.smf.entity.UploadInfo;

import java.util.Date;

public record UploadInfoDTO(String uploadId, String message, Date uploadDate, int fileCount) {
    public static UploadInfoDTO convertToDTO(UploadInfo upload) {
        return new UploadInfoDTO(upload.getUploadId(), upload.getMessage(), upload.getUploadDate(), upload.getFileCount());
    }
}
