package Raadi.controller;
import Raadi.domain.service.QueryServiceService;

import static spark.Spark.get;
import static spark.Spark.port;

public class Query
{
    public static void main(String[] args)
    {
        port(4568);
        get("/query", QueryServiceService.getInstance().setQuery);
    }
}
