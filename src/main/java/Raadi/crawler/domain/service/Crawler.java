package Raadi.crawler.domain.service;

import Raadi.crawler.domain.entity.CrawlerEntity;
import Raadi.crawler.domain.valueObjects.CrawlerVO;
import Raadi.entity.DocumentRaw;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;

public final class Crawler
{

    public static DocumentRaw crawl(CrawlerVO crawlerVO)
    {
        DocumentRaw documentRaw = new DocumentRaw();

        try {
            // Get the content of the page
            Document doc = Jsoup.connect(crawlerVO.getUrl()).get();
            Element content = doc.body();
            String contentRaw = content.text().toLowerCase();

            // Get all the url linked in the page
            Elements links = doc.select("a[href]");
            HashSet<String> childrenURL = new HashSet<>();
            for (Element link : links) {
                String tmp = link.attr("abs:href");
                childrenURL.add(tmp);
            }

            // Fill the document with several informations : url, content, children urls
            documentRaw.setContent(contentRaw);
            documentRaw.setURL(crawlerVO.getUrl());
            documentRaw.setChildrenURL(childrenURL);

        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }

        return documentRaw;
    };
}
