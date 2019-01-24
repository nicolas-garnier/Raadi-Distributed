package Raadi.domain.event;

import Raadi.domain.entity.DocumentRawEntity;

public class DocumentRawCreated extends Event {

    private DocumentRawEntity documentRaw;

    public DocumentRawEntity getDocumentRaw() {
        return documentRaw;
    }

    public void setDocumentRaw(DocumentRawEntity documentRaw) {
        this.documentRaw = documentRaw;
    }

    public DocumentRawCreated(DocumentRawEntity documentRaw)
    {
        super();
        this.documentRaw = documentRaw;
    }
}
