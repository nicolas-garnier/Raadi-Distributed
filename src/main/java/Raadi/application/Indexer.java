package Raadi.application;

import Raadi.domain.entity.EventStoreEntity;
import Raadi.domain.repository.EventStoreRepository;
import Raadi.domain.service.DocumentEventService;
import Raadi.domain.service.QueryEventService;


public class Indexer
{
    @SuppressWarnings("UnnecessaryReturnStatement")
    public static void main(String[] args )
    {
        EventStoreEntity eventStoreEntity = new EventStoreEntity();
        EventStoreRepository eventStoreRepository = new EventStoreRepository(eventStoreEntity);

        new Thread(() ->
        {
            DocumentEventService documentEvent = new DocumentEventService(eventStoreRepository);
            documentEvent.subscribeDocumentRawCreated();
            return;
        }).start();

        new Thread(() ->
        {
            QueryEventService queryEvent = new QueryEventService();
            queryEvent.subscribeTokenizeQuery();
            return;
        }).start();

    }
}
