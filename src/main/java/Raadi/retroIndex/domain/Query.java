package Raadi.retroIndex.domain;

import Raadi.indexer.domain.CleanUp;
import Raadi.Manager;
import Raadi.entity.DocumentClean;
import Raadi.entity.TokenData;

import java.util.ArrayList;
import java.util.HashMap;

public class Query
{
    private String query;
    private HashMap<String, TokenData> vector;
    private HashMap<String, DocumentClean> queryDocuments;

    /***
     *
     * @param query
     */
    public Query(String query)
    {
        this.query = query;
        this.queryDocuments = new HashMap<>();
    }

    /**
     *
     */
    public void tokenisation()
    {
        this.vector = CleanUp.tokenisation(this.query);
        HashMap<String, ArrayList<DocumentClean>> retroIndex = Manager.getInstance().getRetroIndex();

        for(String queryToken : this.vector.keySet())
        {
            if (retroIndex.containsKey(queryToken))
            {
                for (DocumentClean documentClean : retroIndex.get(queryToken))
                {
                    queryDocuments.put(documentClean.getURL(), documentClean);
                }
            }
        }

    }

    public HashMap<String, DocumentClean> getQueryDocuments() {
        return queryDocuments;
    }

}
