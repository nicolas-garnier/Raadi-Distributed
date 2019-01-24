package Raadi.domain.event;

import Raadi.domain.entity.DocumentCleanEntity;

public class DocumentCleanCreated implements Event
{
    private DocumentCleanEntity documentClean;

    public DocumentCleanCreated(DocumentCleanEntity documentClean) {
        this.documentClean = documentClean;
    }

    /**
     * Getter
     * @return
     */
    public DocumentCleanEntity getDocumentClean() {
        return documentClean;
    }

    /**
     * Setter
     * @param documentClean
     */
    public void setDocumentClean(DocumentCleanEntity documentClean) {
        this.documentClean = documentClean;
    }
}