package Raadi.retroindex.domain.service;


import Raadi.entity.DocumentClean;
import Raadi.indexer.domain.event.DocumentCleanCreated;
import Raadi.kafkahandler.KConsumer;
import Raadi.retroindex.domain.entity.RetroIndexEntity;
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
    private KConsumer<String> consumerDocumentCleanCreated;

    /**
     * Start function of the RetroIndexService
     */
    public void start()
    {
        consumerDocumentCleanCreated = new KConsumer<>("DOCUMENT_CLEAN_CREATED", "9092");

        this.subscribeDocumentCleanCreated();
    }

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

                //System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
            }
        }
    }


    public void fillRetroIndex(DocumentClean documentClean)
    {
        for (String key : documentClean.getVector().keySet())
        {
            ArrayList<DocumentClean> value = new ArrayList<>();

            HashMap<String, ArrayList<DocumentClean>> retroIndex = RetroIndexEntity.getInstance().getRetroIndex();

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
