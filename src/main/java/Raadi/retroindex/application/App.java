package Raadi.retroindex.application;

import Raadi.entity.DocumentClean;
import Raadi.framework.RaadiFW;
import Raadi.kafkahandler.KConsumer;
import Raadi.kafkahandler.KHandler;
import Raadi.kafkahandler.KProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


public class App 
{
    public static void main(String[] args)
    {
        RaadiFW raadiFW = new RaadiFW();

        DocumentClean documentClean = new DocumentClean();
        documentClean.setURL("https://google.com");

        KProducer<DocumentClean> producer= new KProducer<>("9092");

        producer.getProducer().send(new ProducerRecord<String, DocumentClean>("TEST", documentClean));
        System.out.println("Message sent successfully");


        KConsumer<DocumentClean> consumer = new KConsumer<>("TEST", "9092");

        while (true)
        {
            ConsumerRecords<String, DocumentClean> records = consumer.getConsumer().poll(Duration.of(1000, ChronoUnit.MILLIS));
            for (ConsumerRecord<String, DocumentClean> record : records)
            {
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value().getURL());
            }
        }


        //raadiFW.bean();
        /*
        manager.execute("https://news.ycombinator.com", 10);

        QueryService query = new QueryService("moored");
        query.tokenisation();

        for (String url : query.getQueryDocuments().keySet())
        {
            System.out.println(url);
        }
        */

        //System.out.println( "Hello World!" );
    }
}
