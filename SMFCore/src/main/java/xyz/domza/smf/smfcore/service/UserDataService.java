package xyz.domza.smf.smfcore.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import xyz.domza.smf.core.RequestZipEvent;

@Service
public class UserDataService {
    private KafkaTemplate<String, RequestZipEvent> kafkaTemplate;

    public UserDataService(KafkaTemplate<String, RequestZipEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String requestZipDownload(String uploadId) throws Exception {
        SendResult<String, RequestZipEvent> result =
                kafkaTemplate.send("zip-requested-events-topic", uploadId, new RequestZipEvent(uploadId)).get();
        return result.toString();
    }
}
