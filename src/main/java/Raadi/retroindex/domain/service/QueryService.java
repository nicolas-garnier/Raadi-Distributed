package Raadi.retroindex.domain.service;

import Raadi.entity.DocumentClean;
import Raadi.entity.TokenData;
import Raadi.indexer.domain.event.QueryTokenized;
import Raadi.kafkahandler.KConsumer;
import Raadi.kafkahandler.KProducer;
import Raadi.retroindex.domain.entity.RetroIndexEntity;
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
    private KConsumer<String> consumerQueryTokenized;
    private KProducer<String> producer;

    /***
     *
     * @param
     */
    public QueryService()
    {
    }

    public void start()
    {
        this.producer = new KProducer<>("9092");
        this.consumerQueryTokenized = new KConsumer<>("QUERY_TOKENIZED", "9092");
    }

    public Route setQuery = (Request request, Response response) ->
    {
        String query = request.queryParams("q");

        this.producer.getProducer().send(new ProducerRecord<>("TOKENIZE_TOKEN", query));
        this.producer.getProducer().close();

        return this.subscribeQueryTokenized();
    };


    /**
     * subscribeQueryTokenized
     */
    private HashMap<String, DocumentClean> subscribeQueryTokenized()
    {

        while (true)
        {

            ConsumerRecords<String, String> records = this.consumerQueryTokenized
                                                        .getConsumer()
                                                        .poll(Duration.of(100, ChronoUnit.MILLIS));

            for (ConsumerRecord<String, String> record : records)
            {
                Gson gson = new Gson();
                Type type = new TypeToken<QueryTokenized>(){}.getType();
                QueryTokenized queryTokenized = gson.fromJson(record.value(), type);
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

        return responseDocuments;
    }

}
