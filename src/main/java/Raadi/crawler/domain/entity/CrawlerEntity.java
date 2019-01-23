package Raadi.crawler.domain.entity;

import Raadi.crawler.domain.valueObjects.CrawlerVO;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Crawler entity class.
 */
public class CrawlerEntity {

    /**
     * List of urls to visit.
     */
    public Queue<CrawlerVO> linksTodo;

    /**
     * Set of url already visited.
     */
    public HashSet<CrawlerVO> linksDone;

    /**
     * Crawler entity constructor.
     */
    public CrawlerEntity() {
        this.linksTodo = new LinkedList<>();
        this.linksDone = new HashSet<>();
    }
}
