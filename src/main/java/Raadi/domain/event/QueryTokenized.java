package Raadi.domain.event;

import java.util.HashMap;
import Raadi.domain.entity.TokenDataEntity;

public class QueryTokenized extends Event
{

    private HashMap<String, TokenDataEntity> vector;

    public HashMap<String, TokenDataEntity> getVector() {
        return vector;
    }

    public void setVector(HashMap<String, TokenDataEntity> vector) {
        this.vector = vector;
    }

    public QueryTokenized(HashMap<String, TokenDataEntity> vector)
    {
        super();
        this.vector = vector;
    }
}
