package Raadi.kafkahandler;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class KConsumer
{
    private String PORT = "9092";
    private String topicName;
    private KafkaConsumer<String, String> consumer;

    public KConsumer(String topicName)
    {
        this.topicName = topicName;

        Properties props = new Properties();

        props.put("bootstrap.servers", "127.0.0.1:"+this.PORT);
        props.put("group.id", "test-group");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        this.consumer = new KafkaConsumer<>(props);

        //Kafka Consumer subscribes list of topics here.
        consumer.subscribe(Arrays.asList(this.topicName));
    }


    public KafkaConsumer<String, String> getConsumer() {
        return consumer;
    }

}
