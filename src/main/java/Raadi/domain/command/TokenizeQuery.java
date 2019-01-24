package Raadi.domain.command;

import Raadi.domain.event.Event;

/**
 * Tokenize query class.
 */
public class TokenizeQuery extends Event {

    /**
     * Attributes.
     */
    private String query;

    /**
     * Tokenize query class.
     * @param query Query to tokenize.
     */
    public TokenizeQuery(String query)
    {
        super();
        this.query = query;
    }

    /**
     * Getter for Query.
     * @return Query as a string.
     */
    public String getQuery() {
        return query;
    }
}
