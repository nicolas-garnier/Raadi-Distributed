package Raadi.domain.entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Retro index entity class.
 */
public class RetroIndexEntity {

    /**
     * Attributes.
     */
    private HashMap<String, ArrayList<DocumentCleanEntity>> retroIndex;

    /**
     * Retro index entity constructor.
     */
    public RetroIndexEntity() {
        this.retroIndex = new HashMap<>();
    }

    /**
     * Getter retro Index.
     * @return HashMap DocumentCleanEntities.
     */
    public HashMap<String, ArrayList<DocumentCleanEntity>> getRetroIndex() {
        return retroIndex;
    }

    /**
     * Setter Retro Index.
     * @param retroIndex Container of clean document entities.
     */
    public void setRetroIndex(HashMap<String, ArrayList<DocumentCleanEntity>> retroIndex) {
        this.retroIndex = retroIndex;
    }
}
