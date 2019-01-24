package Raadi.domain.command;

import Raadi.domain.entity.TokenDataEntity;
import Raadi.domain.event.Event;

import java.util.HashMap;

/**
 * Query process class.
 */
public class ProcessQuery extends Event
{
    /**
     * Attributes.
     */
    private HashMap<String, TokenDataEntity> vector;

    /**
     * Query process constructor.
     * @param vector Computed vector.
     */
    public ProcessQuery(HashMap<String, TokenDataEntity> vector)
    {
        this.vector = vector;
    }

    /**
     * Getter for vector.
     * @return Vector processed on the query.
     */
    public HashMap<String, TokenDataEntity> getVector() {
        return vector;
    }
}
