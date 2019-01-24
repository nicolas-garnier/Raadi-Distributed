package Raadi.domain.service;

import Raadi.domain.entity.DocumentCleanEntity;
import Raadi.domain.entity.DocumentRawEntity;

public class CleanUpService {

    /**
     * Clean up a raw document and convert it to clean document.
     * @param documentRaw Raw document to clean up.
     * @return DocumentCleanEntity.
     */
    public static DocumentCleanEntity cleanup(DocumentRawEntity documentRaw)
    {
        DocumentCleanEntity documentClean = new DocumentCleanEntity();

        documentClean.setContent(documentRaw.getContent());
        documentClean.setChildrenURL(documentRaw.getChildrenURL());
        documentClean.setURL(documentRaw.getURL());
        documentClean.setVector(TokenizationService.tokenization(documentClean.getContent()));

        return documentClean;
    }
}
