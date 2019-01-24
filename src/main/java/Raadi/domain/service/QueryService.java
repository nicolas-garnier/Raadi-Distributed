package Raadi.domain.service;

import Raadi.domain.command.TokenizeQuery;
import Raadi.domain.entity.DocumentCleanEntity;
import Raadi.domain.entity.RetroIndexEntity;
import Raadi.domain.entity.TokenDataEntity;
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
    private HashMap<String, DocumentCleanEntity> subscribeQueryTokenized()
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
    public HashMap<String, DocumentCleanEntity> processTokenizedQuery(HashMap<String, TokenDataEntity> vector)
    {

        HashMap<String, DocumentCleanEntity> responseDocuments = new HashMap<>();
        HashMap<String, ArrayList<DocumentCleanEntity>> retroIndex = RetroIndexEntity.getInstance().getRetroIndex();

        //System.out.println(vector);

        if (vector == null)
            return responseDocuments;

        for(String queryToken : vector.keySet())
        {
            if (retroIndex.containsKey(queryToken))
            {
                for (DocumentCleanEntity documentClean : retroIndex.get(queryToken))
                {
                    responseDocuments.put(documentClean.getURL(), documentClean);
                }
            }
        }

        System.out.println(responseDocuments);
        return responseDocuments;
    }

}
