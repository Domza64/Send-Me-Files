package xyz.domza.smf.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.MimeType;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import org.springframework.http.MediaTypeFactory;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.*;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class S3StorageClient {
    private final S3Client s3Client;
    private final String bucket;

    public S3StorageClient(S3Config config, String bucket) {
        this.s3Client = buildS3Client(config);
        this.bucket = bucket;
        // Try to create bucket if it doesn't exist (for MinIO)
        createBucketIfNotExists();
    }

    private void createBucketIfNotExists() {
        try {
            s3Client.createBucket(CreateBucketRequest.builder()
                    .bucket(bucket)
                    .build());
        } catch (BucketAlreadyExistsException | BucketAlreadyOwnedByYouException e) {
            // Bucket already exists - that's fine
        }
    }

    public List<Bucket> listBuckets() {
        try {
            return s3Client.listBuckets().buckets();
        } catch (S3Exception e) {
            throw new RuntimeException("Failed to list buckets: " + e.getMessage(), e);
        }
    }

    public List<S3Object> listObjects(String bucketName) {
        try {
            ListObjectsV2Request request = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .build();

            return s3Client.listObjectsV2(request).contents();
        } catch (S3Exception e) {
            throw new RuntimeException("Failed to list objects in bucket " + bucketName + ": " + e.getMessage(), e);
        }
    }

    public void uploadFiles(List<MultipartFile> files, String id) {
        for (MultipartFile file : files) {
            try {
                String objectKey = id + "/" + file.getOriginalFilename();

                // Get content type with fallback mechanism
                String contentType = getContentType(file);

                PutObjectRequest request = PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(objectKey)
                        .contentType(contentType)
                        .build();

                // Use optimized byte array upload
                RequestBody requestBody = RequestBody.fromBytes(file.getBytes());

                s3Client.putObject(request, requestBody);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename(), e);
            }
        }
    }

    private String getContentType(MultipartFile file) {
        if (file.getContentType() != null && !file.getContentType().equals("application/octet-stream")) {
            return file.getContentType();
        }

        String filename = file.getOriginalFilename();
        if (filename != null) {
            Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(filename);
            if (mediaType.isPresent()) {
                return mediaType.get().toString();
            }
        }

        try {
            MimeType mimeType = MimeType.valueOf(Arrays.toString(file.getBytes()));
            return mimeType.toString();
        } catch (IOException | IllegalArgumentException e) {
            return "application/octet-stream";
        }
    }


    @Getter
    @Setter
    public static class S3Config {
        private String accessKey;
        private String secretKey;
        private String endpoint;
        private Boolean pathStyleAccess;

        public S3Config(String accessKey, String secretKey) {
            this.accessKey = accessKey;
            this.secretKey = secretKey;
        }
    }

    private S3Client buildS3Client(S3Config config) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                config.getAccessKey(),
                config.getSecretKey()
        );

        S3Configuration.Builder serviceConfigBuilder = S3Configuration.builder();

        // Enable path-style access if specified (needed for MinIO)
        if (config.pathStyleAccess != null) {
            serviceConfigBuilder.pathStyleAccessEnabled(config.pathStyleAccess);
        } else {
            serviceConfigBuilder.pathStyleAccessEnabled(true);
        }

        software.amazon.awssdk.services.s3.S3ClientBuilder clientBuilder = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of("auto"))
                .serviceConfiguration(serviceConfigBuilder.build());

        // Use custom endpoint if provided
        if (config.getEndpoint() != null && !config.getEndpoint().isEmpty()) {
            clientBuilder.endpointOverride(URI.create(config.getEndpoint()));
        }

        return clientBuilder.build();
    }
}
