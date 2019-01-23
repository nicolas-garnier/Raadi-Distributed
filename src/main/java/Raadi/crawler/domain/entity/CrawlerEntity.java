package Raadi.crawler.domain.entity;

import Raadi.crawler.domain.valueObjects.CrawlerVO;
import Raadi.entity.DocumentRaw;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class CrawlerEntity {
    /**
     * List of urls to visit
     */
    public Queue<CrawlerVO> linksTodo;
    /**
     * Set of url already visited
     */
    public HashSet<CrawlerVO> linksDone;

    public CrawlerEntity() {
        this.linksTodo = new LinkedList<>();
        this.linksDone = new HashSet<>();
    }
}
