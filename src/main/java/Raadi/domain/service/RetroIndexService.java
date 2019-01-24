package Raadi.domain.service;


import Raadi.domain.entity.DocumentCleanEntity;
import Raadi.domain.event.DocumentCleanCreated;
import Raadi.kafkahandler.KConsumer;
import Raadi.domain.entity.RetroIndexEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

public class RetroIndexService
{
    private KConsumer consumerDocumentCleanCreated;

    private RetroIndexService()
    {
        consumerDocumentCleanCreated = new KConsumer("DOCUMENT_CLEAN_CREATED");
    }


    public static RetroIndexService getInstance()
    {
        return RetroIndexService.InstanceHolder.instance;
    }

    private static class InstanceHolder {
        private final static RetroIndexService instance = new RetroIndexService();
    }

    /**
     * Start function of the RetroIndexService
     */
    public void start()
    {
        this.subscribeDocumentCleanCreated();
    }

    /**
     * Retrox index subscribe documentClean created
     */
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

    /**
     * Retrox index service
     * @param documentClean
     */
    public void fillRetroIndex(DocumentCleanEntity documentClean)
    {
        System.out.println(documentClean.getURL());

        for (String key : documentClean.getVector().keySet())
        {
            ArrayList<DocumentCleanEntity> value = new ArrayList<>();

            HashMap<String, ArrayList<DocumentCleanEntity>> retroIndex = RetroIndexEntity.getInstance().getRetroIndex();

            if (retroIndex.containsKey(key))
            {
                value = retroIndex.get(key);
            }

            value.add(documentClean);
            retroIndex.put(key, value);

            RetroIndexEntity.getInstance().setRetroIndex(retroIndex);
        }
    }
}
