package Raadi.application;

import Raadi.framework.RaadiFW;

/**
 * Entry point for CrawlerManager application.
 */
public class CrawlerManager {
    /**
     * CrawlerManager application main function.
     * @param args Arguments for main function.
     */
    public static void main(String[] args) {

        final RaadiFW raadi = new RaadiFW();

        raadi.bean(Raadi.domain.service.CrawlerManager.class, new Raadi.domain.service.CrawlerManager());
        Raadi.domain.service.CrawlerManager crawlerManager = (Raadi.domain.service.CrawlerManager) raadi.instanceOf(Raadi.domain.service.CrawlerManager.class);

        new Thread(crawlerManager::subscribeCrawlerCreated).start();
        new Thread(crawlerManager::subscribeDocumentRawCreated).start();
    }
}
