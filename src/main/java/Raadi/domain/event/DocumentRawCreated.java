package Raadi.domain.event;

import Raadi.domain.entity.DocumentRawEntity;

public class DocumentRawCreated extends Event {

    private String idCrawlerService;
    private DocumentRawEntity documentRaw;

    public DocumentRawCreated(DocumentRawEntity documentRaw) {
        super();
        this.documentRaw = documentRaw;
        this.idCrawlerService = "-1";
    }

    public DocumentRawCreated(String idCrawlerService, DocumentRawEntity documentRaw) {
        super();
        this.idCrawlerService = idCrawlerService;
        this.documentRaw = documentRaw;
    }

    /**
     * Getter DocumentRaw
     * @return
     */
    public DocumentRawEntity getDocumentRaw() {
        return documentRaw;
    }

    /**
     * Setter DocumentRaw
     * @param documentRaw
     */
    public void setDocumentRaw(DocumentRawEntity documentRaw) {
        this.documentRaw = documentRaw;
    }

    /**
     * Getter IdCrawlerService
     * @return
     */
    public String getIdCrawlerService() {
        return idCrawlerService;
    }

    /**
     * Setter IdCrawlerService
     * @param idCrawlerService
     */
    public void setIdCrawlerService(String idCrawlerService) {
        this.idCrawlerService = idCrawlerService;
    }
}
