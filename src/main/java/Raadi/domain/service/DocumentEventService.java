package Raadi.domain.service;

import Raadi.domain.entity.DocumentCleanEntity;
import Raadi.domain.event.DocumentRawCreated;
import Raadi.domain.event.DocumentCleanCreated;
import Raadi.domain.repository.EventStoreRepository;
import Raadi.kafkahandler.KConsumer;
import Raadi.kafkahandler.KProducer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DocumentEventService {

    /**
     * Attributes.
     */
    private final KConsumer consumerDocumentRawCreated = new KConsumer("DOCUMENT_RAW_CREATED");
    private final KProducer producer = new KProducer();

    private EventStoreRepository eventStoreRepository;

    public DocumentEventService(EventStoreRepository eventStoreRepository) {
        this.eventStoreRepository = eventStoreRepository;
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

                // EventStore
                eventStoreRepository.insert(record.value(), type);

                // Print
                System.out.println(documentRawCreated.getDocumentRaw().getURL());

                this.publishDocumentCleanCreated(CleanUpService.cleanup(documentRawCreated.getDocumentRaw()));
            }
        }
    }

    /**
     * Publish an event to Kafka when a DocumentCleanEntity has been created.
     * @param documentClean DocumentCleanEntity to publish.
     */
    @SuppressWarnings({"Duplicates", "unchecked"})
    private void publishDocumentCleanCreated(DocumentCleanEntity documentClean) {

        Producer producer = new KProducer().getProducer();
        DocumentCleanCreated documentCleanCreated = new DocumentCleanCreated(documentClean);

        String topicName = "DOCUMENT_CLEAN_CREATED";
        Gson gson = new Gson();
        Type type = new TypeToken<DocumentCleanCreated>() {}.getType();
        String json = gson.toJson(documentCleanCreated, type);
        eventStoreRepository.insert(json, type);
        producer.send(new ProducerRecord<>(topicName, json));
        producer.close();
    }
}