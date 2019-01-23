package Raadi.indexer.domain.service;

import Raadi.entity.DocumentClean;
import Raadi.entity.DocumentRaw;

public class CleanUp {

    /**
     * Clean up a raw document and convert it to clean document.
     * @param documentRaw Raw document to clean up.
     * @return DocumentClean.
     */
    public static DocumentClean cleanup(DocumentRaw documentRaw)
    {
        DocumentClean documentClean = new DocumentClean();

        documentClean.setContent(documentRaw.getContent());
        documentClean.setChildrenURL(documentRaw.getChildrenURL());
        documentClean.setURL(documentRaw.getURL());
        documentClean.setVector(Tokenization.tokenization(documentClean.getContent()));

        return documentClean;
    }
}
