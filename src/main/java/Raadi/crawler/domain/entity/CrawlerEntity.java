package Raadi.crawler.domain.entity;

import Raadi.crawler.domain.valueObjects.CrawlerVO;
import Raadi.entity.DocumentRaw;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class CrawlerEntity {
    public Queue<CrawlerVO> linksTodo;
    public HashSet<CrawlerVO> linksDone;
    public ArrayList<DocumentRaw> documentRawList;

    public CrawlerEntity() {
        this.linksTodo = new LinkedList<>();
        this.linksDone = new HashSet<>();
        this.documentRawList = new ArrayList<>();
    }
}
