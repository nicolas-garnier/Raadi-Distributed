package Raadi.domain.command;

import Raadi.domain.event.Event;
import Raadi.domain.valueObjects.CrawlerVO;

public class CrawlResquest extends Event {
    private String id;
    private CrawlerVO URL;

    public CrawlResquest(String id, CrawlerVO URL) {
        this.id = id;
        this.URL = URL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CrawlerVO getURL() {
        return URL;
    }

    public void setURL(CrawlerVO URL) {
        this.URL = URL;
    }
}
