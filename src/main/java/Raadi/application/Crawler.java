package Raadi.application;

import Raadi.domain.service.CrawlerService;
import Raadi.framework.RaadiFW;

/**
 * Entry point for CrawlerService application.
 */
public class Crawler
{
    /**
     * Crawler application main function.
     * @param args Arguments for main function.
     */
    public static void main(String[] args) {

        final RaadiFW raadi = new RaadiFW();

        raadi.bean(CrawlerService.class, new CrawlerService("https://hackernoon.com"));
        CrawlerService crawlerService = (CrawlerService) raadi.instanceOf(CrawlerService.class);

        new Thread(crawlerService::subscribeCrawlRequest).start();
    }
}