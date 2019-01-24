package Raadi.controller;
import Raadi.domain.service.QueryService;
import Raadi.domain.service.RetroIndexService;

import static spark.Spark.get;
import static spark.Spark.port;

public class QueryController
{
    public static void main(String[] args)
    {
        new Thread(() ->
        {
            RetroIndexService.getInstance().start();
            return;
        }).start();

        port(4568);
        get("/query", QueryService.getInstance().setQuery);
    }
}
