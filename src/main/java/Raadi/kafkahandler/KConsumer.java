package Raadi.kafkahandler;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class KConsumer<T>
{
    private String topicName;
    private String port;
    private KafkaConsumer<String, T> consumer;

    public KConsumer(String topicName, String port)
    {
        this.topicName = topicName;
        this.port = port;


        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:"+this.port);
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serializa-tion.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serializa-tion.StringDeserializer");

        this.consumer = new KafkaConsumer<String, T>(props);

        //Kafka Consumer subscribes list of topics here.
        consumer.subscribe(Arrays.asList(this.topicName));
    }


    public KafkaConsumer<String, T> getConsumer() {
        return consumer;
    }

}
