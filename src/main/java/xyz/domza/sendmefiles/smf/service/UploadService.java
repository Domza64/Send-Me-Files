package xyz.domza.sendmefiles.smf.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.domza.sendmefiles.smf.exception.StorageException;

import java.util.List;
import java.util.Objects;

@Service
public class UploadService {

    public void uploadFile(List<MultipartFile> files, String recipient) throws StorageException {
        // TODO - Implement upload to S3, R2 or something similar, could throw StorageException
        // In future files will be uploaded to specific users. In that case, check if user exists, user accepts all
        // uploads, or just private ones that user requested... Throw necessary Exceptions and implement handler in upload controller

        // check if recipient exists in upload id list, if yes, upload to that user,
        // else it could be a username, search for that username and if exists upload to that user
        // else throw some exception like Recipient not found...
        for (MultipartFile file : files) {
            if (Objects.requireNonNull(file.getOriginalFilename()).endsWith(".txt")) { // Temporary test for throwing StorageException
                throw new StorageException("StorageException thrown to test ExceptionHandling - upload a non txt file for success.");
            }
            System.out.println("Uploading " + file.getOriginalFilename() + " to S3 / R2 or something similar...");
        }
    }
}
