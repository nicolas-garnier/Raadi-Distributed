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
     * Attributes.
     */
    public Queue<CrawlerVO> linksTodo;
    public HashSet<CrawlerVO> linksDone;
    public Integer counter;

    /**
     * CrawlerService constructor.
     */
    public CrawlerEntity() {
        this.linksTodo = new LinkedList<>();
        this.linksDone = new HashSet<>();
        this.counter = 0;
    }
}
