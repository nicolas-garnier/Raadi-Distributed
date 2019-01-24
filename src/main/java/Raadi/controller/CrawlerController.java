package Raadi.controller;

import Raadi.domain.service.CrawlerManager;

import static spark.Spark.get;

public class CrawlerController
{
    public static void main(String[] args)
    {
        get("/crawler", (request, response) ->
        {
            CrawlerManager.getInstance().start(request.queryParams("url"), Integer.parseInt(request.queryParams("size")));
            return "CrawlerService done !";
        });
    }
}
