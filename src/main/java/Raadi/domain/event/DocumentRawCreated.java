package Raadi.domain.event;

import Raadi.domain.entity.DocumentRawEntity;

public class DocumentRawCreated implements Event
{
    private String idCrawlerService;
    private DocumentRawEntity documentRaw;

    public DocumentRawCreated(DocumentRawEntity documentRaw) {
        this.documentRaw = documentRaw;
        this.idCrawlerService = "-1";
    }

    public DocumentRawCreated(String idCrawlerService, DocumentRawEntity documentRaw) {
        this.idCrawlerService = idCrawlerService;
        this.documentRaw = documentRaw;
    }

    public DocumentRawEntity getDocumentRaw() {
        return documentRaw;
    }

    public void setDocumentRaw(DocumentRawEntity documentRaw) {
        this.documentRaw = documentRaw;
    }

    public String getIdCrawlerService() {
        return idCrawlerService;
    }

    public void setIdCrawlerService(String idCrawlerService) {
        this.idCrawlerService = idCrawlerService;
    }
}
