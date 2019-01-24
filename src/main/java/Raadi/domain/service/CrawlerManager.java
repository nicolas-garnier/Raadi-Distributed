package Raadi.domain.service;

import Raadi.domain.entity.CrawlerEntity;
import Raadi.domain.command.CrawlRequest;
import Raadi.domain.event.CrawlerCreated;
import Raadi.domain.event.DocumentRawCreated;
import Raadi.domain.valueObjects.CrawlerVO;
import Raadi.domain.entity.DocumentRawEntity;
import Raadi.kafkahandler.KConsumer;
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

public class CrawlerManager {

    private CrawlerEntity crawlerEntity;
    private ArrayList<String> idAvailableCrawlerService;

    public CrawlerManager() {
        this.crawlerEntity = new CrawlerEntity();
        this.idAvailableCrawlerService = new ArrayList<>();
    }

    public static CrawlerManager getInstance() {
        return CrawlerManager.InstanceHolder.instance;
    }

    private static class InstanceHolder {
        private final static CrawlerManager instance = new CrawlerManager();
    }

    /**
     * Receive "CRAWLER_CREATED" event
     */
    public void subscribeCrawlerCreated() {
        KConsumer consumerCrawlerCreated = new KConsumer("CRAWLER_CREATED");
        System.out.println("SUBSCRIBE CRAWLER CREATED");

        while (true) {
            ConsumerRecords<String, String> records = consumerCrawlerCreated.getConsumer()
                    .poll(Duration.of(100, ChronoUnit.MILLIS));

            for (ConsumerRecord<String, String> record : records) {

                Gson gson = new Gson();
                Type type = new TypeToken<CrawlerCreated>(){}.getType();
                CrawlerCreated crawlerCreated = gson.fromJson(record.value(), type);

                System.out.println("CRAWLER CREATED : " + crawlerCreated.getIdCrawlerService());

                idAvailableCrawlerService.add(crawlerCreated.getIdCrawlerService());

                CrawlerVO crawlerVO = new CrawlerVO(crawlerCreated.getURL());
                this.crawlerEntity.linksDone.add(crawlerVO);
                sendCrawlRequest(crawlerCreated.getIdCrawlerService(), crawlerVO);
            }
        }
    }


    /**
     * Receive "DOCUMENT_RAW_CREATED"
     *      => save children URLs on todolist
     *      => request crawl "CRAWL_REQUEST"
     */
    public void subscribeDocumentRawCreated() {
        KConsumer consumerDocumentRawCreated = new KConsumer("DOCUMENT_RAW_CREATED");
        System.out.println("SUBSCRIBE DOCUMENT RAW CREATED");

        while (true) {
            ConsumerRecords<String, String> records = consumerDocumentRawCreated.getConsumer()
                    .poll(Duration.of(100, ChronoUnit.MILLIS));

            for (ConsumerRecord<String, String> record : records) {

                Gson gson = new Gson();
                Type type = new TypeToken<DocumentRawCreated>() {
                }.getType();
                DocumentRawCreated documentRawCreated = gson.fromJson(record.value(), type);

                DocumentRawEntity documentRaw = documentRawCreated.getDocumentRaw();
                System.out.println("DOCUMENT RAW CREATED : " + documentRaw.getURL());

                if (this.crawlerEntity.counter < 10) {
                    for (String childURL : documentRaw.getChildrenURL()) {
                        this.crawlerEntity.linksTodo.add(new CrawlerVO(childURL));
                    }

                    CrawlerVO URL = this.crawlerEntity.linksTodo.poll();
                    while (this.crawlerEntity.linksDone.contains(URL)) {
                        URL = this.crawlerEntity.linksTodo.poll();
                    }
                    sendCrawlRequest(documentRawCreated.getIdCrawlerService(), URL);
                    this.crawlerEntity.counter++;
                } else {
                    System.out.println("DONE WITH CRAWL REQUEST");
                }
            }
        }
    }

    /**
     * Notifie application via Kafka Crawler created
     * @param idCrawler target crawler
     * @param URL URL to crawl
     */
    private void sendCrawlRequest(String idCrawler, CrawlerVO URL) {
        CrawlRequest crawlRequest = new CrawlRequest(idCrawler, URL);
        String topicName = "CRAWL_REQUEST";

        Gson gson = new Gson();
        Type type = new TypeToken<CrawlRequest>(){}.getType();
        String json = gson.toJson(crawlRequest, type);

        KProducer producer = new KProducer();
        producer.getProducer().send(new ProducerRecord<>(topicName, json));
        producer.getProducer().close();

        System.out.println("CrawlRequest from CrawlerManager's message sent successfully");

    }

}
