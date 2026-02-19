package xyz.domza.smf.smfcore.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import xyz.domza.smf.core.RequestZipEvent;

@Service
public class UserDataService {
    private KafkaTemplate<String, RequestZipEvent> kafkaTemplate;

    public UserDataService(KafkaTemplate<String, RequestZipEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void requestZipDownload(String uploadId) {
        kafkaTemplate.send("zip-requested-events-topic", uploadId, new RequestZipEvent(uploadId));
    }
}
