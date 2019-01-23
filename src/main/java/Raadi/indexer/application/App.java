package Raadi.indexer.application;

import Raadi.indexer.domain.service.DocumentEvent;
import Raadi.indexer.domain.service.QueryEvent;

public class App
{
    public static void main( String[] args )
    {
        DocumentEvent documentEvent = new DocumentEvent();
        QueryEvent queryEvent = new QueryEvent();
        documentEvent.subscribeDocumentRawCreated();
        queryEvent.subscribeTokenizeQuery();
    }
}
