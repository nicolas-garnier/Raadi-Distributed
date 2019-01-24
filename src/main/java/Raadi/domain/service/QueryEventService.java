package Raadi.domain.service;

import Raadi.domain.entity.TokenDataEntity;
import Raadi.domain.event.QueryTokenized;
import Raadi.kafkahandler.KConsumer;
import Raadi.kafkahandler.KProducer;
import Raadi.domain.command.TokenizeQuery;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;


public class QueryEventService {

    /**
     * Attributes.
     */


     /**
     * Subscribe to an event when a QueryController (string) has been published.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void subscribeTokenizeQuery() {

        KConsumer consumerTokenizeQuery = new KConsumer("TOKENIZE_QUERY");
        System.out.println("SUBSCRIBE TOKEN QUERY");
        while (true) {
            ConsumerRecords<String, String> records = consumerTokenizeQuery.getConsumer().poll(Duration.of(100, ChronoUnit.MILLIS));

            for (ConsumerRecord<String, String> record : records) {
                Gson gson = new Gson();
                Type type = new TypeToken<TokenizeQuery>(){}.getType();
                TokenizeQuery tokenizeQuery = gson.fromJson(record.value(), type);

                // Print
                System.out.println("TOKENIZE QUERY : "+tokenizeQuery.getQuery());

                this.publishQueryTokenized(TokenizationService.tokenization(tokenizeQuery.getQuery()));
            }
        }
    }

    /**
     * Publish an event to Kafka when a QueryController (vector) has been tokenized.
     * @param vector Vector to publish.
     */
    @SuppressWarnings({"Duplicates", "unchecked"})
    private void publishQueryTokenized(HashMap<String, TokenDataEntity> vector) {

        KProducer producer = new KProducer();
        QueryTokenized queryTokenized = new QueryTokenized(vector);
        System.out.println("PUBLISH QUERY TOKENIZED");
        String topicName = "QUERY_TOKENIZED";
        Gson gson = new Gson();
        Type type = new TypeToken<QueryTokenized>() {}.getType();
        String json = gson.toJson(queryTokenized, type);
        producer.getProducer().send(new ProducerRecord<>(topicName, json));
        producer.getProducer().close();
    }
}