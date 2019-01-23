package Raadi.domain.model;

import java.util.HashMap;
import java.util.HashSet;

public class DocumentClean {

    /**
     * DocumentClean attributes.
     */
    private String content;
    private String URL;
    private HashSet<String> childrenURL;
    private HashMap<String, TokenData> vector;

    public HashMap<String, TokenData> getVector() {
        return vector;
    }

    public void setVector(HashMap<String, TokenData> vector) {
        this.vector = vector;
    }

    /**
     * DocumentClean content getter.
     * @return the document content.
     */
    public String getContent() {
        return content;
    }

    /**
     * DocumentClean content setter.
     * @param content is the document content.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * DocumentClean URL getter.
     * @return the document URL.
     */
    public String getURL() {
        return URL;
    }

    /**
     * DocumentClean URL setter.
     * @param URL is the document URL.
     */
    public void setURL(String URL) {
        this.URL = URL;
    }

    /**
     * DocumentClean URL children getter.
     * @return the document's list of URL children.
     */
    public HashSet<String> getChildrenURL() {
        return childrenURL;
    }

    /**
     * DocumentClean URL   setter
     * @param childrenURL is list of URL children
     */
    public void setChildrenURL(HashSet<String> childrenURL) {
        this.childrenURL = childrenURL;
    }

    /**
     * DocumentClean constructor.
     */
    public DocumentClean() {
    }
}
