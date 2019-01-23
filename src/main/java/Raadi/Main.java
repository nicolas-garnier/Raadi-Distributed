package Raadi;

import Raadi.kafkahandler.KProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class Main
{
    public static void main(String[] args) {

        KProducer<String> producer = new KProducer<>("9092");
        producer.getProducer().send(new ProducerRecord<>("TEST" ,"CACA"));
        System.out.println("Message sent successfully");
        producer.getProducer().close();

    }
}
