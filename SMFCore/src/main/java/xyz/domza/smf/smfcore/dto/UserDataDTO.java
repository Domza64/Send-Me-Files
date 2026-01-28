package xyz.domza.smf.smfcore.dto;

import java.util.List;

public record UserDataDTO(String username, List<UploadInfoDTO> receivedUploads) {
}
