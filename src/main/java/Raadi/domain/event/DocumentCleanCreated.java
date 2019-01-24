package Raadi.domain.event;

import Raadi.domain.entity.DocumentCleanEntity;

public class DocumentCleanCreated extends Event
{
    private DocumentCleanEntity documentClean;


    public DocumentCleanEntity getDocumentClean() {
        return documentClean;
    }

    public void setDocumentClean(DocumentCleanEntity documentClean) {
        this.documentClean = documentClean;
    }

    public DocumentCleanCreated(DocumentCleanEntity documentClean)
    {
        super();
        this.documentClean = documentClean;
    }
}