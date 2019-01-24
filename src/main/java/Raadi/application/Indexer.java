package Raadi.application;

import Raadi.domain.service.*;
import Raadi.framework.RaadiFW;

/**
 * Entry point for Indexer application.
 */
public class Indexer {

    /**
     * Indexer application's main function.
     * @param args Arguments for main function.
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args )
    {
        final RaadiFW raadi = new RaadiFW();

        raadi.bean(IndexerManagerService.class, new IndexerManagerService());
        IndexerManagerService indexerManagerService = (IndexerManagerService) raadi.instanceOf(IndexerManagerService.class);

        raadi.bean(TokenizationService.class, new TokenizationService(indexerManagerService));
        TokenizationService tokenizationService = (TokenizationService) raadi.instanceOf(TokenizationService.class);

        raadi.bean(CleanUpService.class, new CleanUpService(tokenizationService));
        CleanUpService cleanUpService = (CleanUpService) raadi.instanceOf(CleanUpService.class);


        raadi.bean(DocumentEventService.class, new DocumentEventService(cleanUpService));
        DocumentEventService documentEvent = (DocumentEventService) raadi.instanceOf(DocumentEventService.class);

        raadi.bean(QueryEventService.class, new QueryEventService(tokenizationService));
        QueryEventService queryEvent = (QueryEventService) raadi.instanceOf(QueryEventService.class);

        new Thread(documentEvent::subscribeDocumentRawCreated).start();
        new Thread(queryEvent::subscribeTokenizeQuery).start();
    }
}