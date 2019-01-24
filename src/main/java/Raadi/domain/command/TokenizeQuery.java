package Raadi.domain.command;

import Raadi.domain.event.Event;

public class TokenizeQuery extends Event {
    private String query;

    public TokenizeQuery(String query)
    {
        super();
        this.query = query;
    }

    /**
     * Getter query
     * @return String
     */
    public String getQuery() {
        return query;
    }
}
