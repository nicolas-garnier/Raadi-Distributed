package Raadi.indexer.domain.service;

import Raadi.util.Converter;

import java.util.HashMap;
import java.util.HashSet;

public class IndexerManager
{
    private HashSet<String> stopWords;
    private HashMap<String, String> synonyms;

    private IndexerManager()
    {
        this.stopWords = Converter.StopWordsJsonToHashSet();
        this.synonyms = Converter.SynonymsCSVToHashMap();
    }

    private static class holder {
        private final static IndexerManager instance = new IndexerManager();
    }

    public static IndexerManager getInstance() {return holder.instance;}

    /**
     * IndexerManager stop words getter.
     * @return the stop words.
     */
    public HashSet<String> getStopWords() {
        return stopWords;
    }

    /**
     * IndexerManager synonymes getter.
     * @return the synonymes.
     */
    public HashMap<String, String> getSynonyms() {
        return synonyms;
    }


}
