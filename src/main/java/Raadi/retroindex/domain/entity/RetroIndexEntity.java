package Raadi.retroindex.domain.entity;

import Raadi.Manager;
import Raadi.entity.DocumentClean;

import java.util.ArrayList;
import java.util.HashMap;

public class RetroIndexEntity
{
    private HashMap<String, ArrayList<DocumentClean>> retroIndex;

    private RetroIndexEntity()
    {
        this.retroIndex = new HashMap<>();
    }

    public static RetroIndexEntity getInstance()
    {
        return InstanceHolder.instance;
    }

    private static class InstanceHolder {
        private final static RetroIndexEntity instance = new RetroIndexEntity();
    }

    public HashMap<String, ArrayList<DocumentClean>> getRetroIndex() {
        return retroIndex;
    }

    public void setRetroIndex(HashMap<String, ArrayList<DocumentClean>> retroIndex) {
        this.retroIndex = retroIndex;
    }
}
