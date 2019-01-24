package Raadi.domain.entity;

import Raadi.domain.valueObjects.CrawlerVO;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * CrawlerService entity class.
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
     * CrawlerService entity constructor.
     */
    public CrawlerEntity() {
        this.linksTodo = new LinkedList<>();
        this.linksDone = new HashSet<>();
    }
}
