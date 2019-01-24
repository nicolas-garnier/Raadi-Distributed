package Raadi.domain.service;

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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.UUID;

public final class CrawlerService {
    private String id;
    private String URL;

    public CrawlerService(String initialURL) {
        this.id = UUID.randomUUID().toString();
        this.URL = initialURL;
        sendCrawlerCreated();
    }

    /**
     * Getter id
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Getter URL
     * @return String
     */
    public String getURL() {
        return URL;
    }

    /**
     * Setter URL
     * @param URL
     */
    public void setURL(String URL) {
        this.URL = URL;
    }

    /**
     * The crawl function
     * @param crawlerVO Value Object with url in the content
     */
    private void crawl(CrawlerVO crawlerVO) {
        DocumentRawEntity documentRaw = new DocumentRawEntity();

        try {
            // Get the content of the page
            Document doc = Jsoup.connect(crawlerVO.getUrl()).get();
            Element content = doc.body();
            String contentRaw = content.text().toLowerCase();

            // Get all the url linked in the page
            Elements links = doc.select("a[href]");
            HashSet<String> childrenURL = new HashSet<>();
            for (Element link : links) {
                String tmp = link.attr("abs:href");
                childrenURL.add(tmp);
            }

            // Fill the document with several informations : url, content, children urls
            documentRaw.setContent(contentRaw);
            documentRaw.setURL(crawlerVO.getUrl());
            documentRaw.setChildrenURL(childrenURL);

        } catch (Exception ex) {
            System.err.println(ex);
        }
        sendDocumentRawCreated(documentRaw);
    }

    /**
     * Notifie application via Kafka Crawler created
     */
    private void sendCrawlerCreated() {
        System.out.println("CRAWLER " + this.id + " CREATED");
        CrawlerCreated crawlerCreated = new CrawlerCreated(this.id, this.URL);

        String topicName = "CRAWLER_CREATED";
        Gson gson = new Gson();
        Type type = new TypeToken<CrawlerCreated>(){}.getType();
        String json = gson.toJson(crawlerCreated, type);

        KProducer producer = new KProducer();
        producer.getProducer().send(new ProducerRecord<>(topicName, json));
        producer.getProducer().close();
    }

    /**
     * Notifie application via Kafka DocumentRaw created
     * @param documentRaw Document want to send
     */
    private void sendDocumentRawCreated(DocumentRawEntity documentRaw) {
        System.out.println(documentRaw.getURL());

        DocumentRawCreated documentRawCreated = new DocumentRawCreated(this.id, documentRaw);
        String topicName = "DOCUMENT_RAW_CREATED";
        Gson gson = new Gson();
        Type type = new TypeToken<DocumentRawCreated>(){}.getType();
        String json = gson.toJson(documentRawCreated, type);

        KProducer producer = new KProducer();
        producer.getProducer().send(new ProducerRecord<>(topicName, json));
        producer.getProducer().close();

        System.out.println("CrawlerService's message sent successfully");
    }

    /**
     * Receive "CRAWL_REQUEST" event => init crawl on url
     */
    public void subscribeCrawlRequest() {
        KConsumer consumerCrawlRequest = new KConsumer("CRAWL_REQUEST");
        System.out.println("SUBSCRIBE CRAWL REQUEST");

        while (true) {
            ConsumerRecords<String, String> records = consumerCrawlRequest.getConsumer().poll(Duration.of(100, ChronoUnit.MILLIS));

            for (ConsumerRecord<String, String> record : records) {

                Gson gson = new Gson();
                Type type = new TypeToken<CrawlRequest>(){}.getType();
                CrawlRequest crawlRequest = gson.fromJson(record.value(), type);

                String id = crawlRequest.getId();
                if (this.id.equals(id)) {
                    CrawlerVO crawlerVO = crawlRequest.getURL();
                    System.out.println("CRAWL REQUEST : " + crawlerVO.getUrl());
                    crawl(crawlerVO);
                }
            }
        }
    }


}
