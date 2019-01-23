package Raadi;

import Raadi.indexer.domain.CleanUp;
import Raadi.crawler.domain.Crawler;
import Raadi.entity.DocumentClean;
import Raadi.entity.DocumentRaw;
import Raadi.util.Converter;
import java.util.*;

public class Manager
{
    private Queue<String> linksTodo;
    private HashSet<String> stopWords;
    private HashSet<String> linksDone;
    private ArrayList<DocumentRaw> documentRawList;
    private ArrayList<DocumentClean> documentCleanList;
    private HashMap<String, String> synonyms;

    private HashMap<String, ArrayList<DocumentClean>> retroIndex;


    private Manager() {
        this.linksTodo = new LinkedList<>();
        this.linksDone = new HashSet<>();
        this.documentRawList = new ArrayList<>();
        this.documentCleanList = new ArrayList<>();
        this.stopWords = Converter.StopWordsJsonToHashSet();
        this.synonyms = Converter.SynonymsCSVToHashMap();
        this.retroIndex = new HashMap<>();
    }

    private static class ManagerHolder {
        private final static Manager instance = new Manager();
    }

    public static Manager getInstance() {
        return ManagerHolder.instance;
    }

    /**
     * Manager stop words getter.
     * @return the stop words.
     */
    public HashSet<String> getStopWords() {
        return stopWords;
    }

    /**
     * Manager synonymes getter.
     * @return the synonymes.
     */
    public HashMap<String, String> getSynonyms() {
        return synonyms;
    }

    public void execute(String firstURL, int max_size) {
        crawl(firstURL, max_size);
        cleanup();
        fillRetroIndex();

        System.out.println(documentCleanList.size());
        System.out.println(retroIndex);
    }

    /**
     * Manager crawl function to fill the documentRawList.
     * @param firstURL is the entry url.
     * @param max_size is the maximun url number wanted.
     */
    private void crawl(String firstURL, int max_size) {
        linksTodo.add(firstURL);

        while (documentRawList.size() < max_size && !linksTodo.isEmpty()) {
            String url = linksTodo.poll();

            if (!linksDone.contains(url)) {
                DocumentRaw dr = Crawler.crawl(url);
                if (dr != null) {
                    documentRawList.add(dr);
                    linksDone.add(url);

                    linksTodo.addAll(dr.getChildrenURL());
                }
            }
        }
    }

    public HashMap<String, ArrayList<DocumentClean>> getRetroIndex() {
        return retroIndex;
    }

    /**
     * documentCleanList content setter.
     */
    private void cleanup() {
        for (DocumentRaw documentRaw : documentRawList) {
            documentCleanList.add(CleanUp.cleanup(documentRaw));
        }
        documentRawList.clear();
    }

    /**
     * retroindex content setter.
     */
    private void fillRetroIndex() {
        for (DocumentClean dc : documentCleanList) {
            for (String key : dc.getVector().keySet()) {
                ArrayList<DocumentClean> value = new ArrayList<>();
                if (retroIndex.containsKey(key))
                    value = retroIndex.get(key);
                value.add(dc);
                retroIndex.put(key, value);
            }
        }
    }
}
