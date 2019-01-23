package Raadi.crawler.domain.valueObjects;

public class CrawlerVO {
    private String url;

    public CrawlerVO(String url) {
        this.url = url;
    }

    /**
     * Getter for URL
     * @return String url
     */
    public String getUrl() {
        return url;
    }
}
