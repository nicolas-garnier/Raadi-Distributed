package Raadi.indexer.domain.event;

import Raadi.entity.DocumentClean;

public class DocumentCleanCreated {
    private DocumentClean documentClean;

    public DocumentClean getDocumentClean() {
        return documentClean;
    }

    public void setDocumentClean(DocumentClean documentClean) {
        this.documentClean = documentClean;
    }

    public DocumentCleanCreated(DocumentClean documentClean) {
        this.documentClean = documentClean;
    }
}