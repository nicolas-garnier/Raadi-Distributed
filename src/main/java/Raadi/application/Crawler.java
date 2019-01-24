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
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        final RaadiFW raadi = new RaadiFW();

        raadi.bean(CrawlerService.class, new CrawlerService("https://hackernoon.com/what-we-can-learn-from-cryptos-anti-hero-302f6346c524"));
        CrawlerService crawlerService = (CrawlerService) raadi.instanceOf(CrawlerService.class);

        new Thread(crawlerService::subscribeCrawlRequest).start();
    }
}