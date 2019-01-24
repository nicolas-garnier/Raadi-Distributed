package Raadi.indexer.application;

import Raadi.indexer.domain.service.DocumentEvent;
import Raadi.indexer.domain.service.QueryEvent;


public class App
{
    public static void main( String[] args )
    {
        DocumentEvent documentEvent = new DocumentEvent();
        documentEvent.subscribeDocumentRawCreated();

        // TODO : Async call
        QueryEvent queryEvent = new QueryEvent();
        queryEvent.subscribeTokenizeQuery();
    }
}
