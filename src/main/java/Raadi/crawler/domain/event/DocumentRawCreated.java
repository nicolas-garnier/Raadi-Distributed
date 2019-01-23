package Raadi.crawler.domain.event;

import Raadi.entity.DocumentRaw;

public class DocumentRawCreated {

    private DocumentRaw documentRaw;

    public DocumentRawCreated(DocumentRaw documentRaw) {
        this.documentRaw = documentRaw;
    }
}
