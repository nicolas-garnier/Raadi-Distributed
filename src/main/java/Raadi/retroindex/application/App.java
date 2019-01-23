package Raadi.retroindex.application;

import Raadi.entity.DocumentClean;
import Raadi.framework.RaadiFW;
import Raadi.kafkahandler.KConsumer;
import Raadi.kafkahandler.KProducer;
import Raadi.retroindex.controller.Query;
import Raadi.retroindex.domain.service.QueryService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


public class App 
{
    public static void main(String[] args)
    {
        /*
        QueryService queryService = new QueryService();
        queryService.start();

       */

        System.out.println( "RetroIndex started" );
    }
}
