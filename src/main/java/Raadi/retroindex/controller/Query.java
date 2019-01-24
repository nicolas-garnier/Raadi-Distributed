package Raadi.retroindex.controller;
import Raadi.retroindex.domain.service.QueryService;

import static spark.Spark.get;
import static spark.Spark.port;

public class Query
{
    public static void main(String[] args)
    {
        port(4568);
        get("/query", QueryService.getInstance().setQuery);
    }
}
