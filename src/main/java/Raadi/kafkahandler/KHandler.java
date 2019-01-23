package Raadi.kafkahandler;


public class KHandler
{
    private KConsumer kConsumer;
    private KProducer kProducer;

    private String PORT = "9092";
    private String TOPIC_NAME = "raadi";


    private KHandler()
    {
        this.kProducer = new KProducer(this.PORT);
        this.kConsumer = new KConsumer(this.TOPIC_NAME, this.PORT);
    }

    private static class InstanceHolder
    {
        private final static KHandler instance = new KHandler();
    }

    public static KHandler getInstance() { return InstanceHolder.instance;}

    public KConsumer getkConsumer() {
        return kConsumer;
    }

    public KProducer getkProducer() {
        return kProducer;
    }
}
