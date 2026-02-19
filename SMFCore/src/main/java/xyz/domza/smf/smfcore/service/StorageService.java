package xyz.domza.smf.smfcore.service;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.S3Object;
import xyz.domza.smf.core.S3StorageClient;
import java.util.ArrayList;
import java.util.List;

@Service
public class StorageService {
    private Environment env;
    private S3StorageClient s3StorageClient;

    public StorageService(Environment env) {
        this.env = env;
        setupCloudflareClient();
    }

    private void setupCloudflareClient() {
        String accessKey = env.getProperty("storage.access-key");
        String secretKey = env.getProperty("storage.secret-key");
        String bucket = env.getProperty("storage.bucket");
        String customEndpoint = env.getProperty("storage.endpoint");
        String pathStyleAccess = env.getProperty("storage.path-style-access");

        S3StorageClient.S3Config config = new S3StorageClient.S3Config(
                accessKey,
                secretKey
        );
        config.setEndpoint(customEndpoint);
        config.setPathStyleAccess(Boolean.parseBoolean(pathStyleAccess));

        this.s3StorageClient = new S3StorageClient(config, bucket);
    }

    public void uploadFiles(List<MultipartFile> files, String id) {
        s3StorageClient.uploadFiles(files, id);
    }

    public List<Bucket> listBuckets() {
        return s3StorageClient.listBuckets();
    }

    public List<String> listObjects(String path) {
        // Works now with 5 like 15 objects :/ Good luck later :)
        List<S3Object> files = s3StorageClient.listObjects("upload-bucket"); // TODO: Load from env
        List<String> fileNames = new ArrayList<>(files.size());
        files.forEach(file -> {
            if (file.key().startsWith(path)) {
                fileNames.add(file.key());
            }
        });
        return fileNames;
    }
}
