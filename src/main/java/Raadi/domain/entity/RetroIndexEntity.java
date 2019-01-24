package Raadi.domain.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class RetroIndexEntity
{
    private HashMap<String, ArrayList<DocumentCleanEntity>> retroIndex;

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

    public HashMap<String, ArrayList<DocumentCleanEntity>> getRetroIndex() {
        return retroIndex;
    }

    public void setRetroIndex(HashMap<String, ArrayList<DocumentCleanEntity>> retroIndex) {
        this.retroIndex = retroIndex;
    }
}
