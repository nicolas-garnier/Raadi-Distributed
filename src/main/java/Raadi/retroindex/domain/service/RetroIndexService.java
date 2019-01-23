package Raadi.retroindex.domain.service;

import Raadi.entity.DocumentClean;
import Raadi.retroindex.domain.entity.RetroIndexEntity;

import java.util.ArrayList;
import java.util.HashMap;

public class RetroIndexService
{
    public void fillRetroIndex(DocumentClean documentClean)
    {
        for (String key : documentClean.getVector().keySet())
        {
            ArrayList<DocumentClean> value = new ArrayList<>();

            HashMap<String, ArrayList<DocumentClean>> retroIndex = RetroIndexEntity.getInstance().getRetroIndex();

            if (retroIndex.containsKey(key))
            {
                value = retroIndex.get(key);
            }

            value.add(documentClean);
            retroIndex.put(key, value);

            RetroIndexEntity.getInstance().setRetroIndex(retroIndex);
        }
    }
}
