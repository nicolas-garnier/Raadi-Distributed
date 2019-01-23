package Raadi.retroindex.domain.service;

import Raadi.Manager;
import Raadi.entity.DocumentClean;
import Raadi.entity.TokenData;
import Raadi.indexer.domain.service.Tokenization;
import Raadi.kafkahandler.KConsumer;
import Raadi.kafkahandler.KProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

public class QueryService
{
    private String query;
    private HashMap<String, TokenData> vector;
    private HashMap<String, DocumentClean> queryDocuments;

    private KConsumer<String> consumerQueryTokenized;
    private KProducer<String> producer;

    /***
     *
     * @param query
     */
    public QueryService(String query)
    {
        this.query = query;
        this.queryDocuments = new HashMap<>();
    }

    public void start()
    {
        this.producer = new KProducer<>("9092");
        this.consumerQueryTokenized = new KConsumer<>("QUERY_TOKENIZED", "9092");
        this.subscribeQueryTokenized();
    }

    /**
     * subscribeQueryTokenized
     */
    private void subscribeQueryTokenized()
    {
        while (true)
        {
            ConsumerRecords<String, String> records = this.consumerQueryTokenized.getConsumer().poll(Duration.of(1000, ChronoUnit.MILLIS));
            for (ConsumerRecord<String, String> record : records)
            {

                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
            }
        }
    }


    /**
     * Tokenization
     */
    public void tokenization()
    {
        this.vector = Tokenization.tokenization(this.query);
        HashMap<String, ArrayList<DocumentClean>> retroIndex = Manager.getInstance().getRetroIndex();

        for(String queryToken : this.vector.keySet())
        {
            if (retroIndex.containsKey(queryToken))
            {
                for (DocumentClean documentClean : retroIndex.get(queryToken))
                {
                    queryDocuments.put(documentClean.getURL(), documentClean);
                }
            }
        }

    }

    public HashMap<String, DocumentClean> getQueryDocuments() {
        return queryDocuments;
    }

}
