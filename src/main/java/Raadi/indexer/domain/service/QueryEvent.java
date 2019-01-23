package Raadi.indexer.domain.service;

import Raadi.entity.TokenData;
import Raadi.indexer.domain.event.QueryTokenized;
import Raadi.kafkahandler.KConsumer;
import Raadi.kafkahandler.KProducer;
import Raadi.retroindex.domain.command.TokenizeQuery;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;


public class QueryEvent {

    /**
     * Attributes.
     */
    private final KConsumer<String> consumerTokenizeQuery = new KConsumer<>("TOKENIZE_QUERY", "9092");
    private final KProducer<String> producer = new KProducer<>("9092");


     /**
     * Subscribe to an event when a Query (string) has been published.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void subscribeTokenizeQuery() {

        while (true) {
            ConsumerRecords<String, String> records = this.consumerTokenizeQuery.getConsumer().poll(Duration.of(1000, ChronoUnit.MILLIS));

            for (ConsumerRecord<String, String> record : records) {
                Gson gson = new Gson();
                Type type = new TypeToken<TokenizeQuery>(){}.getType();
                TokenizeQuery tokenizeQuery = gson.fromJson(record.value(), type);
                publishQueryTokenized(Tokenization.tokenization(tokenizeQuery.query));
            }
        }
    }

    /**
     * Publish an event to Kafka when a Query (vector) has been tokenized.
     * @param vector Vector to publish.
     */
    @SuppressWarnings({"Duplicates", "unchecked"})
    private void publishQueryTokenized(HashMap<String, TokenData> vector) {

        Producer producer = this.producer.getProducer();
        QueryTokenized queryTokenized = new QueryTokenized(vector);

        String topicName = "QUERY_TOKENIZED";
        Gson gson = new Gson();
        Type type = new TypeToken<QueryTokenized>() {}.getType();
        String json = gson.toJson(queryTokenized, type);
        producer.send(new ProducerRecord<>(topicName, json));
        producer.close();
    }
}