package xyz.domza.smf.smfcore.service;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.S3Object;
import xyz.domza.smf.smfcore.cloudflare.CloudflareR2Client;
import java.util.ArrayList;
import java.util.List;

@Service
public class CloudflareService {
    private Environment env;
    private CloudflareR2Client r2Client;

    public CloudflareService(Environment env) {
        this.env = env;
        setupCloudflareClient();
    }

    private void setupCloudflareClient() {
        String accountId = env.getProperty("cloudflare.account-id");
        String accessKey = env.getProperty("cloudflare.access-key");
        String secretKey = env.getProperty("cloudflare.secret-key");
        String bucket = env.getProperty("cloudflare.bucket");
        String customEndpoint = env.getProperty("S3_ENDPOINT");
        String pathStyleAccess = env.getProperty("S3_PATH_STYLE_ACCESS");

        CloudflareR2Client.S3Config config = new CloudflareR2Client.S3Config(
                accountId,
                accessKey,
                secretKey
        );
        if (customEndpoint != null && !customEndpoint.isEmpty()) {
            config.setEndpoint(customEndpoint);
        }
        if (pathStyleAccess != null) {
            config.setPathStyleAccess(Boolean.parseBoolean(pathStyleAccess));
        }
        this.r2Client = new CloudflareR2Client(config, bucket);
    }

    public void uploadFiles(List<MultipartFile> files, String id) {
        r2Client.uploadFiles(files, id);
    }

    public List<Bucket> listBuckets() {
        return r2Client.listBuckets();
    }

    public List<String> listObjects(String path) {
        // Works now with 5 like 15 objects :/ Good luck later :)
        List<S3Object> files = r2Client.listObjects("smf-uploads-dev");
        List<String> fileNames = new ArrayList<>(files.size());
        files.forEach(file -> {
            if (file.key().startsWith(path)) {
                fileNames.add(file.key());
            }
        });
        return fileNames;
    }
}
