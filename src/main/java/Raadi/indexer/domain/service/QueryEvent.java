package Raadi.indexer.domain.service;

import Raadi.entity.TokenData;
import Raadi.indexer.domain.event.QueryTokenized;
import Raadi.kafkahandler.KProducer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.lang.reflect.Type;
import java.util.HashMap;

public class QueryEvent {

    /**
     * Subscribe to an event when a Query (string) has been published.
     */
    public void subscribeTokenizeQuery() {
        // TODO Deserialize Query
    }

    /**
     * Publish an event to Kafka when a Query (vector) has been tokenized.
     * @param vector Vector to publish.
     */
    public void publishQueryTokenized(HashMap<String, TokenData> vector) {

        Producer producer = new KProducer("9092").getProducer();
        QueryTokenized queryTokenized = new QueryTokenized(vector);

        String topicName = "QUERY_TOKENIZED";
        Gson gson = new Gson();
        Type type = new TypeToken<QueryTokenized>() {}.getType();
        String json = gson.toJson(queryTokenized, type);
        producer.send(new ProducerRecord<>(topicName, json));
        producer.close();
    }
}