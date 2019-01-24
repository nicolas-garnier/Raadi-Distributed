package Raadi.crawler.controller;

import Raadi.crawler.domain.service.CrawlerManager;

import static spark.Spark.get;

public class Crawler
{
    public static void main(String[] args)
    {
        get("/crawler", (request, response) ->
        {
            CrawlerManager.getInstance().start(request.queryParams("url"), Integer.parseInt(request.queryParams("size")));
            return "Crawler done !";
        });
    }
}
