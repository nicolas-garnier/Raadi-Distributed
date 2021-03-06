package Raadi.domain.service;

import Raadi.domain.command.ProcessQuery;
import Raadi.domain.command.TokenizeQuery;
import Raadi.domain.entity.DocumentCleanEntity;
import Raadi.domain.entity.TokenDataEntity;
import Raadi.domain.event.QueryResponse;
import Raadi.domain.event.QueryTokenized;
import Raadi.kafkahandler.KConsumer;
import Raadi.kafkahandler.KProducer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;


public class QueryService
{

    public QueryService()
    {
    }

    /**
     * Query service setQuery
     * @param request
     * @param response
     * @return Route
     */
    public Route setQuery = (Request request, Response response) ->
    {
        String query = request.queryParams("q");

        KProducer producer = new KProducer();
        TokenizeQuery tokenizeQuery = new TokenizeQuery(query);
        Gson gson = new Gson();
        Type type = new TypeToken<TokenizeQuery>() {}.getType();
        String json = gson.toJson(tokenizeQuery, type);

        // Step 1
        // Send command to Indexer for tokenization of the query
        producer.getProducer().send(new ProducerRecord<>("TOKENIZE_QUERY", json));
        producer.getProducer().close();
        System.out.println("QUERY : "+query);

        HashMap<String, DocumentCleanEntity> hashMap = this.subscribeQueryTokenized();

        return hashMap;
    };


    /**
     * Query service subscribeQueryTokenized : subscribe to event QUERY_TOKENIZED (from Indexer)
     */
    private HashMap<String, DocumentCleanEntity> subscribeQueryTokenized()
    {

        KConsumer consumer = new KConsumer("QUERY_TOKENIZED");

        while (true)
        {
            ConsumerRecords<String, String> records = consumer.getConsumer().poll(Duration.of(100, ChronoUnit.MILLIS));

            // Step 2
            for (ConsumerRecord<String, String> record : records)
            {
                Gson gson = new Gson();
                Type type = new TypeToken<QueryTokenized>(){}.getType();
                QueryTokenized queryTokenized = gson.fromJson(record.value(), type);
                System.out.println("QUERY TOKENIZED");
                this.sendTokenizedQuery(queryTokenized.getVector());

                // Subscribe for response
                return this.subscribeQueryResponse();
            }
        }
    }

    /**
     * sendTokenizedQuery to RetroIndex
     * @param vector
     */
    private void sendTokenizedQuery(HashMap<String, TokenDataEntity> vector)
    {
        KProducer producer = new KProducer();
        ProcessQuery processQuery = new ProcessQuery(vector);
        Gson gson = new Gson();
        Type type = new TypeToken<ProcessQuery>() {}.getType();
        String json = gson.toJson(processQuery, type);

        // Step 3
        // Send command to RetroIndex for Process Query
        producer.getProducer().send(new ProducerRecord<>("PROCESS_QUERY", json));
        producer.getProducer().close();
    }

    /**
     * subscribeQueryResponse : subscribe to the event QUERY_RESPONSE
     * @return
     */
    private HashMap<String, DocumentCleanEntity> subscribeQueryResponse()
    {
        KConsumer consumer = new KConsumer("QUERY_RESPONSE");

        while (true)
        {
            ConsumerRecords<String, String> records = consumer.getConsumer().poll(Duration.of(100, ChronoUnit.MILLIS));

            // Step 4
            for (ConsumerRecord<String, String> record : records)
            {
                Gson gson = new Gson();
                Type type = new TypeToken<QueryResponse>(){}.getType();
                QueryResponse queryResponse = gson.fromJson(record.value(), type);
                System.out.println("QUERY RESPONSE");
                System.out.println(queryResponse.getResponses());

                // Return response (documents corresponding to a query)
                return queryResponse.getResponses();
            }
        }
    }

}
