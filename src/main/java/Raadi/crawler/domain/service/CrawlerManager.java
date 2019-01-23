package Raadi.crawler.domain.service;

import Raadi.crawler.domain.entity.CrawlerEntity;
import Raadi.crawler.domain.event.DocumentRawCreated;
import Raadi.crawler.domain.valueObjects.CrawlerVO;
import Raadi.entity.DocumentRaw;
import Raadi.kafkahandler.KProducer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.lang.reflect.Type;

public class CrawlerManager {
    private CrawlerEntity crawlerEntity;

    public CrawlerManager() {
        this.crawlerEntity = new CrawlerEntity();

    }

    /**
     * Start the crawling on the url in parameter
     * @param firstURL the url to start
     * @param max_size Number of page wanted to visited
     */
    private void start(String firstURL, int max_size) {
        CrawlerVO crawlerVO = new CrawlerVO(firstURL);
        this.crawlerEntity.linksTodo.add(crawlerVO);
        int counter = 0;

        while (counter <= max_size && !this.crawlerEntity.linksTodo.isEmpty()) {
            CrawlerVO url = this.crawlerEntity.linksTodo.poll();

            if (!this.crawlerEntity.linksDone.contains(url)) {
                DocumentRaw dr = Crawler.crawl(url);
                if (dr != null) {
                    this.crawlerEntity.linksDone.add(url);
                    counter++;

                    for(String childURL : dr.getChildrenURL()) {
                        this.crawlerEntity.linksTodo.add(new CrawlerVO(childURL));
                    }
                    send(dr);
                }
            }
        }
    }

    /**
     * Notifie application via Kafka
     * @param documentRaw Document want to send
     */
    public void send(DocumentRaw documentRaw) {
        Producer<String, String> producer = new KProducer("9092").getProducer();
        DocumentRawCreated documentRawCreated = new DocumentRawCreated(documentRaw);

        String topicName = "DOCUMENT_RAW_CREATED";
        Gson gson = new Gson();
        Type type = new TypeToken<DocumentRawCreated>(){}.getType();
        String json = gson.toJson(documentRawCreated, type);

        producer.send(new ProducerRecord<>(topicName, json));

        System.out.println("Crawler's message sent successfully");
        producer.close();
    }
}
