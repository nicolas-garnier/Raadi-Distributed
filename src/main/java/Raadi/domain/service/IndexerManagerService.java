package Raadi.domain.service;

import Raadi.util.Converter;

import java.util.HashMap;
import java.util.HashSet;

public class IndexerManagerService
{
    private HashSet<String> stopWords;
    private HashMap<String, String> synonyms;

    private IndexerManagerService()
    {
        this.stopWords = Converter.StopWordsJsonToHashSet();
        this.synonyms = Converter.SynonymsCSVToHashMap();
    }

    private static class holder {
        private final static IndexerManagerService instance = new IndexerManagerService();
    }

    public static IndexerManagerService getInstance() {return holder.instance;}

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
