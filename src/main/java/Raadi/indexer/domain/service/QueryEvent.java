package Raadi.indexer.domain.service;

import Raadi.entity.TokenData;
import Raadi.indexer.domain.event.QueryTokenized;
import Raadi.kafkahandler.KProducer;
import com.google.gson.Gson;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;

public class QueryEvent {

    public void subscribeTokenizeQuery() {
        // TODO Deserialize Query
    }

    public void publishQueryTokenized(HashMap<String, TokenData> vector) {

        Producer producer = new KProducer("9092").getProducer();
        QueryTokenized queryTokenized = new QueryTokenized(vector);

        String topicName = "QUERY_TOKENIZED";
        Gson gson = new Gson();
        String json = gson.toJson(queryTokenized);
        producer.send(new ProducerRecord<>(topicName, json));
        producer.close();
    }
}