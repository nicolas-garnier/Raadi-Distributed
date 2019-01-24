package Raadi.controller;
import Raadi.domain.service.QueryServiceService;
import Raadi.domain.service.RetroIndexService;

import static spark.Spark.get;
import static spark.Spark.port;

public class Query
{
    public static void main(String[] args)
    {
        new Thread(() ->
        {
            RetroIndexService.getInstance().start();
            return;
        }).start();

        port(4568);
        get("/query", QueryServiceService.getInstance().setQuery);
    }
}
