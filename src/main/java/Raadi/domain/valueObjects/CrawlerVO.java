package Raadi.domain.valueObjects;

/**
 * Crawler value object class.
 */
public class CrawlerVO {

    /**
     * Attributes.
     */
    private String url;

    /**
     * Crawler value object constructor.
     * @param url URL crawled.
     */
    public CrawlerVO(String url) {
        this.url = url;
    }

    /**
     * Getter for URL.
     * @return String url.
     */
    public String getUrl() {
        return url;
    }
}
