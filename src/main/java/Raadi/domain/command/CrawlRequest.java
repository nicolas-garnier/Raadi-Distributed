package Raadi.domain.command;

import Raadi.domain.event.Event;
import Raadi.domain.valueObjects.CrawlerVO;

/**
 * Crawl request class.
 */
public class CrawlRequest extends Event {

    /**
     * Attributes.
     */
    private String id;
    private CrawlerVO URL;

    /**
     * Crawl request constructor.
     * @param id Crawl ID.
     * @param URL Crawl URL.
     */
    public CrawlRequest(String id, CrawlerVO URL) {
        this.id = id;
        this.URL = URL;
    }

    /**
     * Getter for ID.
     * @return Crawl ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for ID.
     * @param id Crawl ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for ID.
     * @return Crawl URL.
     */
    public CrawlerVO getURL() {
        return URL;
    }

    /**
     * Setter for URL.
     * @param URL Crawl URL.
     */
    public void setURL(CrawlerVO URL) {
        this.URL = URL;
    }
}
