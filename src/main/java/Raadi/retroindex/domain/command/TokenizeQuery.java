package Raadi.retroindex.domain.command;

public class TokenizeQuery
{
    private String query;

    public TokenizeQuery(String query)
    {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
