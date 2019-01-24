package Raadi.domain.event;

import Raadi.domain.entity.DocumentCleanEntity;

import java.util.HashMap;

public class QueryResponse
{
    HashMap<String, DocumentCleanEntity> responses;

    public QueryResponse(HashMap<String, DocumentCleanEntity> responses)
    {
        this.responses = responses;
    }

    public HashMap<String, DocumentCleanEntity> getResponses() {
        return responses;
    }
}
