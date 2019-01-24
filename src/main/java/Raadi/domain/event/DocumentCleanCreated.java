package Raadi.domain.event;

import Raadi.domain.entity.DocumentCleanEntity;

public class DocumentCleanCreated extends Event
{
    private DocumentCleanEntity documentClean;

    public DocumentCleanCreated(DocumentCleanEntity documentClean) {
        super();
        this.documentClean = documentClean;
    }

    /**
     * Getter DocumentClean
     * @return
     */
    public DocumentCleanEntity getDocumentClean() {
        return documentClean;
    }

    /**
     * Setter DocumentClean
     * @param documentClean
     */
    public void setDocumentClean(DocumentCleanEntity documentClean) {
        this.documentClean = documentClean;
    }

}