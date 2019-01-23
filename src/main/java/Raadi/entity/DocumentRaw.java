package Raadi.entity;

import Raadi.crawler.domain.valueObjects.CrawlerVO;

import java.util.HashSet;

public class DocumentRaw {

    /**
     * DocumentRaw attributes.
     */
    private String content;
    private String URL;
    private HashSet<CrawlerVO> childrenURL;

    /**
     * DocumentRaw content getter.
     * @return the document content.
     */
    public String getContent() {
        return content;
    }

    /**
     * DocumentRaw content setter.
     * @param content is the document content.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * DocumentRaw URL getter.
     * @return the document URL.
     */
    public String getURL() {
        return URL;
    }

    /**
     * DocumentRaw URL setter.
     * @param URL is the document URL.
     */
    public void setURL(String URL) {
        this.URL = URL;
    }

    /**
     * DocumentRaw URL children getter.
     * @return the document's list of URL children.
     */
    public HashSet<CrawlerVO> getChildrenURL() {
        return childrenURL;
    }

    /**
     * DocumentRaw URL   setter
     * @param childrenURL is list of URL children
     */
    public void setChildrenURL(HashSet<CrawlerVO> childrenURL) {
        this.childrenURL = childrenURL;
    }

    /**
     * DocumentRaw constructor.
     */
    public DocumentRaw() {
    }
}
