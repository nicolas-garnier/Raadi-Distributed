package Raadi.crawler.domain.event;

import Raadi.entity.DocumentRaw;
import Raadi.kafkahandler.KHandler;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class CrawlerEvents {

    private KHandler kHandler;

    public CrawlerEvents() {
        this.kHandler = KHandler.getInstance();
    }

    public void send(DocumentRaw documentRaw) {
        String topicName = "CRAWLER_EVENT";
        Producer<String, String> producer = kHandler.getkProducer().getProducer();
        producer.send(new ProducerRecord<>(topicName, documentRaw));
        System.out.println("Crawler's message sent successfully");
        producer.close();
    }
}
