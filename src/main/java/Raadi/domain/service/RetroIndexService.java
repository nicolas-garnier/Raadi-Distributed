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

    public RetroIndexService() {
        this.consumerDocumentCleanCreated = new KConsumer("DOCUMENT_CLEAN_CREATED");
    }

    /**
     * Start function of the RetroIndexService.
     */
    public void start() {
        this.subscribeDocumentCleanCreated();
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
}
