package Raadi.crawler.domain.event;

import Raadi.entity.DocumentRaw;

public class DocumentRawCreated {

    private DocumentRaw documentRaw;

    public DocumentRaw getDocumentRaw() {
        return documentRaw;
    }

    public void setDocumentRaw(DocumentRaw documentRaw) {
        this.documentRaw = documentRaw;
    }

    public DocumentRawCreated(DocumentRaw documentRaw) {
        this.documentRaw = documentRaw;
    }
}
