package Raadi.application;


import Raadi.domain.service.CrawlerManager;

/**
 * Entry point for CrawlerService application.
 */
public class Crawler
{

    /**
     * CrawlerService application main function.
     * @param args Arguments for main function.
     */
    public static void main(String[] args)
    {
        System.out.println("CrawlerService started !");
        CrawlerManager.getInstance().start("https://hackernoon.com/personal-insurance-is-there-a-better-way-enter-policypal-8aac627c877a", 3);

    }
}