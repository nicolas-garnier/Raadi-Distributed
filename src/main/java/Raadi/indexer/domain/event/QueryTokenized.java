package Raadi.indexer.domain.event;

import java.util.HashMap;
import Raadi.entity.TokenData;

public class QueryTokenized {

    private HashMap<String, TokenData> vector;

    public HashMap<String, TokenData> getVector() {
        return vector;
    }

    public void setVector(HashMap<String, TokenData> vector) {
        this.vector = vector;
    }

    public QueryTokenized(HashMap<String, TokenData> vector) {
        this.vector = vector;
    }
}
