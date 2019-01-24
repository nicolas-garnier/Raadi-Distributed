package Raadi.domain.service;

import Raadi.domain.entity.DocumentCleanEntity;
import Raadi.domain.entity.DocumentRawEntity;

public class CleanUpService {

    private TokenizationService tokenizationService;

    public CleanUpService(TokenizationService tokenizationService) {
        this.tokenizationService = tokenizationService;
    }
    /**
     * Clean up a raw document and convert it to clean document.
     * @param documentRaw Raw document to clean up.
     * @return DocumentCleanEntity.
     */
    DocumentCleanEntity cleanup(DocumentRawEntity documentRaw)
    {
        DocumentCleanEntity documentClean = new DocumentCleanEntity();

        documentClean.setContent(documentRaw.getContent());
        documentClean.setChildrenURL(documentRaw.getChildrenURL());
        documentClean.setURL(documentRaw.getURL());
        documentClean.setVector(tokenizationService.tokenization(documentClean.getContent()));

        return documentClean;
    }
}
