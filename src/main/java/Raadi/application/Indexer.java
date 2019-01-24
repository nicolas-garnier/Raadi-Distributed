package Raadi.application;

import Raadi.domain.service.DocumentEventService;
import Raadi.domain.service.QueryEventService;


public class Indexer
{
    public static void main( String[] args )
    {
        new Thread(() ->
        {
            DocumentEventService documentEvent = new DocumentEventService();
            documentEvent.subscribeDocumentRawCreated();
            return;
        }).start();

        new Thread(() ->
        {
            QueryEventService queryEvent = new QueryEventService();
            queryEvent.subscribeTokenizeQuery();
        }).start();

    }
}
