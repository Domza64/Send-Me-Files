package xyz.domza.smf.smfcore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.domza.smf.smfcore.service.UploadService;

import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    private final UploadService uploadService;

    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping()
    public ResponseEntity<String> handleFileUpload(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("recipient") String recipient,
            @RequestParam("message") String message) {
        uploadService.uploadFiles(files, recipient, message);
        return ResponseEntity.ok("File uploaded successfully.");
    }
}
