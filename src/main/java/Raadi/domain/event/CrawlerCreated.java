package Raadi.domain.event;

public class CrawlerCreated implements Event {
    private String idCrawlerService;
    private String URL;

    public CrawlerCreated(String idCrawlerService, String URL) {
        this.idCrawlerService = idCrawlerService;
        this.URL = URL;
    }

    public String getIdCrawlerService() {
        return idCrawlerService;
    }

    public void setIdCrawlerService(String idCrawlerService) {
        this.idCrawlerService = idCrawlerService;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
