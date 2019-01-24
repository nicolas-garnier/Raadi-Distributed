package Raadi.domain.service;

import java.util.HashMap;
import java.util.HashSet;

public class IndexerManagerService
{
    /**
     * Attributes.
     */
    private HashSet<String> stopWords;
    private HashMap<String, String> synonyms;

    /**
     * Indexer manager service constructor.
     */
    public IndexerManagerService() {
        this.stopWords = ConverterService.StopWordsJsonToHashSet();
        this.synonyms = ConverterService.SynonymsCSVToHashMap();
    }

    /**
     * IndexerManagerService stop words getter.
     * @return the stop words.
     */
    public HashSet<String> getStopWords() {
        return stopWords;
    }

    /**
     * IndexerManagerService synonymes getter.
     * @return the synonymes.
     */
    public HashMap<String, String> getSynonyms() {
        return synonyms;
    }


}
