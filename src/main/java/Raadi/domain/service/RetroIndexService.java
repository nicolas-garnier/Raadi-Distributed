package Raadi.domain.service;


import Raadi.domain.command.ProcessQuery;
import Raadi.domain.entity.DocumentCleanEntity;
import Raadi.domain.entity.TokenDataEntity;
import Raadi.domain.event.DocumentCleanCreated;
import Raadi.domain.event.QueryResponse;
import Raadi.kafkahandler.KConsumer;
import Raadi.domain.entity.RetroIndexEntity;
import Raadi.kafkahandler.KProducer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

public class RetroIndexService
{
    private KConsumer consumerDocumentCleanCreated;
    private KConsumer consumerProcessQuery;

    public RetroIndexService()
    {
        consumerDocumentCleanCreated = new KConsumer("DOCUMENT_CLEAN_CREATED");
        consumerProcessQuery = new KConsumer("PROCESS_QUERY");

    }

    /**
     * Start function of the RetroIndexService.
     */
    public void start() {
        this.subscribeDocumentCleanCreated();
        this.subscribeProcessQuery();
    }

    /**
     * Retro index subscribe documentClean created.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    private void subscribeDocumentCleanCreated()
    {
        while (true)
        {
            ConsumerRecords<String, String> records = this.consumerDocumentCleanCreated.getConsumer().poll(Duration.of(1000, ChronoUnit.MILLIS));
            for (ConsumerRecord<String, String> record : records)
            {
                Gson gson = new Gson();
                Type type = new TypeToken<DocumentCleanCreated>(){}.getType();
                DocumentCleanCreated documentCleanCreated = gson.fromJson(record.value(), type);
                this.fillRetroIndex(documentCleanCreated.getDocumentClean());
            }
        }
    }

    private void subscribeProcessQuery()
    {
        while (true)
        {
            ConsumerRecords<String, String> records = this.consumerProcessQuery.getConsumer().poll(Duration.of(1000, ChronoUnit.MILLIS));
            for (ConsumerRecord<String, String> record : records)
            {
                Gson gson = new Gson();
                Type type = new TypeToken<ProcessQuery>(){}.getType();
                ProcessQuery processQuery = gson.fromJson(record.value(), type);
                this.sendQueryResponse(processQuery.getVector());
            }
        }
    }

    private void sendQueryResponse(HashMap<String, TokenDataEntity> vector)
    {
        HashMap<String, DocumentCleanEntity> hashMap = this.processQuery(vector);

        KProducer producer = new KProducer();
        QueryResponse queryResponse = new QueryResponse(hashMap);
        Gson gson = new Gson();
        Type type = new TypeToken<QueryResponse>() {}.getType();
        String json = gson.toJson(queryResponse, type);

        // Step 3
        producer.getProducer().send(new ProducerRecord<>("QUERY_RESPONSE", json));
        producer.getProducer().close();
    }

    /**
     * Retrox index service.
     * @param documentClean Document clean to fill.
     */
    private void fillRetroIndex(DocumentCleanEntity documentClean) {

        System.out.println(documentClean.getURL());

        for (String key : documentClean.getVector().keySet()) {

            ArrayList<DocumentCleanEntity> value = new ArrayList<>();
            HashMap<String, ArrayList<DocumentCleanEntity>> retroIndex = RetroIndexEntity.getInstance().getRetroIndex();

            if (retroIndex.containsKey(key)) {
                value = retroIndex.get(key);
            }
            value.add(documentClean);
            retroIndex.put(key, value);
            RetroIndexEntity.getInstance().setRetroIndex(retroIndex);
        }
    }

    public HashMap<String, DocumentCleanEntity> processQuery(HashMap<String, TokenDataEntity> vector)
    {

        HashMap<String, DocumentCleanEntity> responseDocuments = new HashMap<>();


        HashMap<String, ArrayList<DocumentCleanEntity>> retroIndex = RetroIndexEntity.getInstance().getRetroIndex();


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
