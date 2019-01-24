package Raadi.domain.service;

import Raadi.domain.entity.CrawlerEntity;
import Raadi.domain.event.DocumentRawCreated;
import Raadi.domain.valueObjects.CrawlerVO;
import Raadi.domain.entity.DocumentRawEntity;
import Raadi.kafkahandler.KProducer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.lang.reflect.Type;

public class CrawlerManager {
    private CrawlerEntity crawlerEntity;

    private CrawlerManager()
    {
        this.crawlerEntity = new CrawlerEntity();
    }

    public static CrawlerManager getInstance()
    {
        return CrawlerManager.InstanceHolder.instance;
    }

    private static class InstanceHolder {
        private final static CrawlerManager instance = new CrawlerManager();
    }

    /**
     * Start the crawling on the url in parameter
     * @param firstURL the url to start
     * @param max_size Number of page wanted to visited
     */
    public void start(String firstURL, int max_size) {
        CrawlerVO crawlerVO = new CrawlerVO(firstURL);
        this.crawlerEntity.linksTodo.add(crawlerVO);
        int counter = 0;

        while (counter <= max_size && !this.crawlerEntity.linksTodo.isEmpty()) {
            CrawlerVO url = this.crawlerEntity.linksTodo.poll();

            if (!this.crawlerEntity.linksDone.contains(url)) {
                DocumentRawEntity dr = CrawlerService.crawl(url);
                if (dr != null) {
                    this.crawlerEntity.linksDone.add(url);
                    counter++;

                    for(String childURL : dr.getChildrenURL()) {
                        this.crawlerEntity.linksTodo.add(new CrawlerVO(childURL));
                    }
                    this.send(dr);
                }
            }
        }

        this.crawlerEntity.linksTodo.clear();
    }

    /**
     * Notifie application via Kafka
     * @param documentRaw Document want to send
     */
    public void send(DocumentRawEntity documentRaw)
    {
        System.out.println(documentRaw.getURL());
        //System.out.println(documentRaw.getChildrenURL());

        DocumentRawCreated documentRawCreated = new DocumentRawCreated(documentRaw);
        String topicName = "DOCUMENT_RAW_CREATED";
        Gson gson = new Gson();
        Type type = new TypeToken<DocumentRawCreated>(){}.getType();
        String json = gson.toJson(documentRawCreated, type);

        KProducer producer = new KProducer();
        producer.getProducer().send(new ProducerRecord<>(topicName, json));
        producer.getProducer().close();

        System.out.println("CrawlerService's message sent successfully");
    }
}
