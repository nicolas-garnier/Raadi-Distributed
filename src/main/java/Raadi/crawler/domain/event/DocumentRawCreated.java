package Raadi.crawler.domain.event;

import Raadi.entity.DocumentRaw;

public class DocumentRawCreated {

    private DocumentRaw documentRaw;

    /**
     * Class for the Event
     * @param documentRaw page crawled
     */
    public DocumentRawCreated(DocumentRaw documentRaw) {
        this.documentRaw = documentRaw;
    }
}
