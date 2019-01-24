package Raadi.crawler.application;


import Raadi.crawler.domain.service.CrawlerManager;

/**
 * Entry point for Crawler application.
 */
public class App
{

    /**
     * Crawler application main function.
     * @param args Arguments for main function.
     */
    public static void main(String[] args)
    {
        System.out.println("Crawler started !");
        CrawlerManager.getInstance().start("https://hackernoon.com/personal-insurance-is-there-a-better-way-enter-policypal-8aac627c877a", 3);

    }
}