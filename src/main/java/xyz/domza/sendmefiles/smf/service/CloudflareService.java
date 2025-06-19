package xyz.domza.sendmefiles.smf.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.Bucket;
import xyz.domza.sendmefiles.smf.cloudflare.CloudflareR2Client;
import java.util.List;

@Service
public class CloudflareService {
    private final CloudflareR2Client r2Client;

    public CloudflareService() {
        // TODO - Load from env file
        CloudflareR2Client.S3Config config = new CloudflareR2Client.S3Config(
                "",
                "",
                ""
        );
        this.r2Client = new CloudflareR2Client(config);
    }

    public void uploadFiles(List<MultipartFile> files, String id) {
        r2Client.uploadFiles(files, id);
    }

    public List<Bucket> listBuckets() {
        return r2Client.listBuckets();
    }
}
