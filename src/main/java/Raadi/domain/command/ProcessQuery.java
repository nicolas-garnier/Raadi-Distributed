package Raadi.domain.command;

import Raadi.domain.entity.TokenDataEntity;
import Raadi.domain.event.Event;

import java.util.HashMap;

public class ProcessQuery extends Event
{
    private HashMap<String, TokenDataEntity> vector;

    public ProcessQuery(HashMap<String, TokenDataEntity> vector)
    {
        this.vector = vector;
    }

    public HashMap<String, TokenDataEntity> getVector() {
        return vector;
    }
}
