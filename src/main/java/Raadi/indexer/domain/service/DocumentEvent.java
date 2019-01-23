package Raadi.indexer.domain.service;

import Raadi.entity.DocumentClean;
import Raadi.indexer.domain.event.DocumentCleanCreated;
import Raadi.kafkahandler.KProducer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.lang.reflect.Type;

public class DocumentEvent {

    /**
     * Subscribe to an event when a DocumentRaw has been published.
     */
    public void subscribeDocumentRawCreated() {
        // TODO Deserialize DocumentRaw
    }

    /**
     * Publish an event to Kafka when a DocumentClean has been created.
     * @param documentClean DocumentClean to publish.
     */
    public void publishDocumentCleanCreated(DocumentClean documentClean) {

        Producer producer = new KProducer("9092").getProducer();
        DocumentCleanCreated documentCleanCreated = new DocumentCleanCreated(documentClean);

        String topicName = "DOCUMENT_CLEAN_CREATED";
        Gson gson = new Gson();
        Type type = new TypeToken<DocumentCleanCreated>() {}.getType();
        String json = gson.toJson(documentCleanCreated, type);
        producer.send(new ProducerRecord<>(topicName, json));
        producer.close();
    }
}