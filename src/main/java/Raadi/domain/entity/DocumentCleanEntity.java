package Raadi.domain.entity;

import java.util.HashMap;
import java.util.HashSet;

public class DocumentCleanEntity {

    /**
     * DocumentCleanEntity attributes.
     */
    private String content;
    private String URL;
    private HashSet<String> childrenURL;
    private HashMap<String, TokenDataEntity> vector;

    public HashMap<String, TokenDataEntity> getVector() {
        return vector;
    }

    public void setVector(HashMap<String, TokenDataEntity> vector) {
        this.vector = vector;
    }

    /**
     * DocumentCleanEntity content getter.
     * @return the document content.
     */
    public String getContent() {
        return content;
    }

    /**
     * DocumentCleanEntity content setter.
     * @param content is the document content.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * DocumentCleanEntity URL getter.
     * @return the document URL.
     */
    public String getURL() {
        return URL;
    }

    /**
     * DocumentCleanEntity URL setter.
     * @param URL is the document URL.
     */
    public void setURL(String URL) {
        this.URL = URL;
    }

    /**
     * DocumentCleanEntity URL children getter.
     * @return the document's list of URL children.
     */
    public HashSet<String> getChildrenURL() {
        return childrenURL;
    }

    /**
     * DocumentCleanEntity URL   setter
     * @param childrenURL is list of URL children
     */
    public void setChildrenURL(HashSet<String> childrenURL) {
        this.childrenURL = childrenURL;
    }

    /**
     * DocumentCleanEntity constructor.
     */
    public DocumentCleanEntity() {
    }
}
