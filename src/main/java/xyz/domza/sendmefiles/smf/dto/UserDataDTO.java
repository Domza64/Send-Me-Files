package xyz.domza.sendmefiles.smf.dto;

import java.util.List;

public record UserDataDTO(String username, List<String> recievedUploadsIds) {
}
