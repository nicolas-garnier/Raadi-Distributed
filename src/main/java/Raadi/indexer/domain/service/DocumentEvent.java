package Raadi.indexer.domain.service;

import Raadi.crawler.domain.event.DocumentRawCreated;
import Raadi.entity.DocumentClean;
import Raadi.indexer.domain.event.DocumentCleanCreated;
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

public class DocumentEvent {

    /**
     * Attributes.
     */
    private final KConsumer consumerDocumentRawCreated = new KConsumer("DOCUMENT_RAW_CREATED");
    private final KProducer producer = new KProducer();

    /**
     * Subscribe to an event when a DocumentRaw has been published.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void subscribeDocumentRawCreated() {
        while (true) {
            ConsumerRecords<String, String> records = this.consumerDocumentRawCreated.getConsumer().poll(Duration.of(1000, ChronoUnit.MILLIS));

            for (ConsumerRecord<String, String> record : records) {
                Gson gson = new Gson();
                Type type = new TypeToken<DocumentRawCreated>(){}.getType();
                DocumentRawCreated documentRawCreated = gson.fromJson(record.value(), type);

                // Print
                System.out.println(documentRawCreated.getDocumentRaw().getURL());

                publishDocumentCleanCreated(CleanUp.cleanup(documentRawCreated.getDocumentRaw()));
            }
        }
    }

    /**
     * Publish an event to Kafka when a DocumentClean has been created.
     * @param documentClean DocumentClean to publish.
     */
    @SuppressWarnings({"Duplicates", "unchecked"})
    public void publishDocumentCleanCreated(DocumentClean documentClean) {

        Producer producer = new KProducer().getProducer();
        DocumentCleanCreated documentCleanCreated = new DocumentCleanCreated(documentClean);

        String topicName = "DOCUMENT_CLEAN_CREATED";
        Gson gson = new Gson();
        Type type = new TypeToken<DocumentCleanCreated>() {}.getType();
        String json = gson.toJson(documentCleanCreated, type);
        producer.send(new ProducerRecord<>(topicName, json));
        producer.close();
    }
}