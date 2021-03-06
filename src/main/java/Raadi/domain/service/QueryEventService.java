package Raadi.domain.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;
import Raadi.domain.entity.TokenDataEntity;
import Raadi.domain.event.QueryTokenized;
import Raadi.kafkahandler.KConsumer;
import Raadi.kafkahandler.KProducer;
import Raadi.domain.command.TokenizeQuery;

/**
 * Service class for query events management.
 */
public class QueryEventService {

    /**
     * Attributes.
     */
    private KConsumer consumerTokenizeQuery;
    private TokenizationService tokenizationService;

    /**
     * Query events service constructor.
     * @param tokenizationService Tokenization service to inject.
     */
    public QueryEventService(TokenizationService tokenizationService) {
        this.consumerTokenizeQuery = new KConsumer("TOKENIZE_QUERY");
        this.tokenizationService = tokenizationService;
    }

     /**
     * Subscribe to an event when a QueryController (string) has been published.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void subscribeTokenizeQuery() {

        System.out.println("SUBSCRIBE TOKEN QUERY");
        while (true) {
            ConsumerRecords<String, String> records = consumerTokenizeQuery
                    .getConsumer()
                    .poll(Duration.of(100, ChronoUnit.MILLIS));

            for (ConsumerRecord<String, String> record : records) {
                Gson gson = new Gson();
                Type type = new TypeToken<TokenizeQuery>(){}.getType();
                TokenizeQuery tokenizeQuery = gson.fromJson(record.value(), type);

                System.out.println("TOKENIZE QUERY : "+tokenizeQuery.getQuery());
                HashMap<String, TokenDataEntity> tokens = tokenizationService.tokenization(tokenizeQuery.getQuery());
                this.publishQueryTokenized(tokens);
            }
        }
    }

    /**
     * Publish an event to Kafka when a QueryController (vector) has been tokenized.
     * @param vector Vector to publish.
     */
    @SuppressWarnings({"Duplicates", "unchecked"})
    private void publishQueryTokenized(HashMap<String, TokenDataEntity> vector) {

        System.out.println(vector);
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