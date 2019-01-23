package Raadi.indexer.domain.service;

import Raadi.entity.DocumentClean;
import Raadi.indexer.domain.event.DocumentCleanCreated;
import Raadi.kafkahandler.KProducer;
import com.google.gson.Gson;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class DocumentEvent {

    public void subscribeDocumentRawCreated() {
        // TODO Deserialize DocumentRaw
    }

    public void publishDocumentCleanCreated(DocumentClean documentClean) {

        Producer producer = new KProducer("9092").getProducer();
        DocumentCleanCreated documentCleanCreated = new DocumentCleanCreated(documentClean);

        String topicName = "DOCUMENT_CLEAN_CREATED";
        Gson gson = new Gson();
        String json = gson.toJson(documentCleanCreated);
        producer.send(new ProducerRecord<>(topicName, json));
        producer.close();
    }
}