package Raadi.application;

import Raadi.domain.service.CleanUpService;
import Raadi.domain.service.DocumentEventService;
import Raadi.domain.service.QueryEventService;
import Raadi.domain.service.TokenizationService;
import Raadi.framework.RaadiFW;


public class Indexer {

    @SuppressWarnings("unchecked")
    public static void main(String[] args )
    {
        final RaadiFW raadi = new RaadiFW();

        raadi.bean(TokenizationService.class, new TokenizationService());
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