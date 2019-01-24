package Raadi.domain.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;
import Raadi.domain.entity.DocumentCleanEntity;
import Raadi.domain.event.DocumentRawCreated;
import Raadi.domain.event.DocumentCleanCreated;
import Raadi.kafkahandler.KConsumer;
import Raadi.kafkahandler.KProducer;

/**
 * Service class for document events management.
 */
public class DocumentEventService {

    /**
     * Attributes.
     */
    private KConsumer consumerDocumentRawCreated;
    private KProducer producer;
    private CleanUpService cleanUpService;

    /**
     * Document events service constructor.
     * @param cleanUpService Cleanup service to inject.
     */
    public DocumentEventService(CleanUpService cleanUpService) {
        this.consumerDocumentRawCreated = new KConsumer("DOCUMENT_RAW_CREATED");
        this.producer = new KProducer();
        this.cleanUpService = cleanUpService;
    }

    /**
     * Subscribe to an event when a DocumentRawEntity has been published.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void subscribeDocumentRawCreated() {

        System.out.println("SUBSCRIBE DOCUMENT RAW CREATED");
        while (true) {
            ConsumerRecords<String, String> records = this.consumerDocumentRawCreated
                    .getConsumer()
                    .poll(Duration.of(1000, ChronoUnit.MILLIS));

            for (ConsumerRecord<String, String> record : records) {
                Gson gson = new Gson();
                Type type = new TypeToken<DocumentRawCreated>(){}.getType();
                DocumentRawCreated documentRawCreated = gson.fromJson(record.value(), type);

                System.out.println(documentRawCreated.getDocumentRaw().getURL());
                this.publishDocumentCleanCreated(cleanUpService.cleanup(documentRawCreated.getDocumentRaw()));
            }
        }
    }

    /**
     * Publish an event to Kafka when a DocumentCleanEntity has been created.
     * @param documentClean DocumentCleanEntity to publish.
     */
    @SuppressWarnings({"Duplicates", "unchecked"})
    private void publishDocumentCleanCreated(DocumentCleanEntity documentClean) {

        DocumentCleanCreated documentCleanCreated = new DocumentCleanCreated(documentClean);
        System.out.println("PUBLISH DOCUMENT CLEAN CREATED");
        String topicName = "DOCUMENT_CLEAN_CREATED";
        Gson gson = new Gson();
        Type type = new TypeToken<DocumentCleanCreated>() {}.getType();
        String json = gson.toJson(documentCleanCreated, type);
        producer.getProducer().send(new ProducerRecord<>(topicName, json));
        producer.getProducer().close();
    }
}