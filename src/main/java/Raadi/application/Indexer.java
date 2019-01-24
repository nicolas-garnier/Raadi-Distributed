package Raadi.application;

import Raadi.domain.service.DocumentEventService;
import Raadi.domain.service.QueryEventService;


public class Indexer
{
    public static void main( String[] args )
    {
        DocumentEventService documentEvent = new DocumentEventService();
        documentEvent.subscribeDocumentRawCreated();

        // TODO : Async call
        QueryEventService queryEvent = new QueryEventService();
        queryEvent.subscribeTokenizeQuery();
    }
}
