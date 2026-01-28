package xyz.domza.smf.filezippermicroservice.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import xyz.domza.smf.core.RequestZipEvent;

@Component
@KafkaListener(topics="zip-requested-events-topic")
public class ZipRequestEventHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaHandler
    public void handle(RequestZipEvent requestZipEvent) {
        LOGGER.info("Received a new zip request for uploadId: " + requestZipEvent.uploadId());
        // TODO: Create zip for data in this upload, upload it to a zip bucket and publish zip created event
    }
}
