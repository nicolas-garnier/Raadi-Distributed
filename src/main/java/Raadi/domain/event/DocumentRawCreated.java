package Raadi.domain.event;

import Raadi.domain.entity.DocumentRawEntity;

public class DocumentRawCreated implements Event
{

    private DocumentRawEntity documentRaw;

    public DocumentRawEntity getDocumentRaw() {
        return documentRaw;
    }

    public void setDocumentRaw(DocumentRawEntity documentRaw) {
        this.documentRaw = documentRaw;
    }

    public DocumentRawCreated(DocumentRawEntity documentRaw) {
        this.documentRaw = documentRaw;
    }
}
