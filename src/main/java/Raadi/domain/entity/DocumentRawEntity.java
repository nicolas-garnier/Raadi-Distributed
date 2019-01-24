package Raadi.domain.entity;

import java.util.HashSet;

public class DocumentRawEntity {

    /**
     * DocumentRawEntity attributes.
     */
    private String content;
    private String URL;
    private HashSet<String> childrenURL;

    /**
     * DocumentRawEntity content getter.
     * @return the document content.
     */
    public String getContent() {
        return content;
    }

    /**
     * DocumentRawEntity content setter.
     * @param content is the document content.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * DocumentRawEntity URL getter.
     * @return the document URL.
     */
    public String getURL() {
        return URL;
    }

    /**
     * DocumentRawEntity URL setter.
     * @param URL is the document URL.
     */
    public void setURL(String URL) {
        this.URL = URL;
    }

    /**
     * DocumentRawEntity URL children getter.
     * @return the document's list of URL children.
     */
    public HashSet<String> getChildrenURL() {
        return childrenURL;
    }

    /**
     * DocumentRawEntity URL   setter
     * @param childrenURL is list of URL children
     */
    public void setChildrenURL(HashSet<String> childrenURL) {
        this.childrenURL = childrenURL;
    }

    /**
     * DocumentRawEntity constructor.
     */
    public DocumentRawEntity() {
    }
}
