package Raadi.retroindex.domain.service;

import Raadi.entity.DocumentClean;
import Raadi.entity.TokenData;
import Raadi.indexer.domain.event.QueryTokenized;
import Raadi.kafkahandler.KConsumer;
import Raadi.kafkahandler.KProducer;
import Raadi.retroindex.domain.command.TokenizeQuery;
import Raadi.retroindex.domain.entity.RetroIndexEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;


public class QueryService
{
    /***
     *
     * @param
     */
    private QueryService()
    {
    }

    public static QueryService getInstance()
    {
        return QueryService.InstanceHolder.instance;
    }

    private static class InstanceHolder
    {
        private final static QueryService instance = new QueryService();
    }


    public Route setQuery = (Request request, Response response) ->
    {
        String query = request.queryParams("q");

        KProducer producer = new KProducer();
        TokenizeQuery tokenizeQuery = new TokenizeQuery(query);
        Gson gson = new Gson();
        Type type = new TypeToken<TokenizeQuery>() {}.getType();
        String json = gson.toJson(tokenizeQuery, type);

        producer.getProducer().send(new ProducerRecord<>("TOKENIZE_QUERY", json));
        producer.getProducer().close();
        System.out.println("QUERY : "+query);
        this.subscribeQueryTokenized();

        return "finish";
    };


    /**
     * subscribeQueryTokenized
     */
    private HashMap<String, DocumentClean> subscribeQueryTokenized()
    {

        KConsumer consumer = new KConsumer("QUERY_TOKENIZED");

        while (true)
        {
            ConsumerRecords<String, String> records = consumer.getConsumer().poll(Duration.of(100, ChronoUnit.MILLIS));

            for (ConsumerRecord<String, String> record : records)
            {
                System.out.println("WESH");
                Gson gson = new Gson();
                Type type = new TypeToken<QueryTokenized>(){}.getType();
                QueryTokenized queryTokenized = gson.fromJson(record.value(), type);
                System.out.println("QUERY TOKENIZED");
                return this.processTokenizedQuery(queryTokenized.getVector());
            }
        }
    }


    /**
     * processTokenizedQuery
     * @param vector
     * @return
     */
    public HashMap<String, DocumentClean> processTokenizedQuery(HashMap<String, TokenData> vector)
    {

        HashMap<String, DocumentClean> responseDocuments = new HashMap<>();
        HashMap<String, ArrayList<DocumentClean>> retroIndex = RetroIndexEntity.getInstance().getRetroIndex();

        //System.out.println(vector);

        if (vector == null)
            return responseDocuments;

        for(String queryToken : vector.keySet())
        {
            if (retroIndex.containsKey(queryToken))
            {
                for (DocumentClean documentClean : retroIndex.get(queryToken))
                {
                    responseDocuments.put(documentClean.getURL(), documentClean);
                }
            }
        }

        System.out.println(responseDocuments);
        return responseDocuments;
    }

}
