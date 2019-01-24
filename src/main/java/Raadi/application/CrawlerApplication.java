package Raadi.application;

import Raadi.domain.service.CrawlerManager;
import Raadi.framework.RaadiFW;

/**
 * Entry point for Crawler application.
 */
public class CrawlerApplication {

    /**
     * Crawler application's main function.
     * @param args Arguments for main function.
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        final RaadiFW raadi = new RaadiFW();

        raadi.bean(Raadi.domain.service.CrawlerManager.class, new Raadi.domain.service.CrawlerManager());
        CrawlerManager crawlerManager = (CrawlerManager) raadi.instanceOf(CrawlerManager.class);

        new Thread(crawlerManager::subscribeCrawlerCreated).start();
        new Thread(crawlerManager::subscribeDocumentRawCreated).start();
    }
}
