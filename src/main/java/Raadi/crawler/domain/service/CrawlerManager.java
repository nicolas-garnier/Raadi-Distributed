package Raadi.crawler.domain.service;

import Raadi.crawler.domain.entity.CrawlerEntity;
import Raadi.crawler.domain.valueObjects.CrawlerVO;
import Raadi.entity.DocumentRaw;

public class CrawlerManager {

    private CrawlerEntity crawlerEntity;

    public CrawlerManager() {
        this.crawlerEntity = new CrawlerEntity();
    }

    private void start(String firstURL, int max_size) {
        CrawlerVO crawlerVO = new CrawlerVO(firstURL);
        this.crawlerEntity.linksTodo.add(crawlerVO);

        while (this.crawlerEntity.documentRawList.size() < max_size && !this.crawlerEntity.linksTodo.isEmpty()) {
            CrawlerVO url = this.crawlerEntity.linksTodo.poll();

            if (!this.crawlerEntity.linksDone.contains(url)) {
                DocumentRaw dr = Crawler.crawl(url);
                if (dr != null) {
                    this.crawlerEntity.documentRawList.add(dr);
                    this.crawlerEntity.linksDone.add(url);

                    this.crawlerEntity.linksTodo.addAll(dr.getChildrenURL());
                }
            }
        }
    }
}
