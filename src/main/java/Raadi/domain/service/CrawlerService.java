package Raadi.domain.service;

import Raadi.domain.valueObjects.CrawlerVO;
import Raadi.domain.entity.DocumentRawEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;

public final class CrawlerService
{
    /**
     * The crawl function
     * @param crawlerVO Value Object with url in the content
     * @return DocumentRawEntity : page informations
     */
    public static DocumentRawEntity crawl(CrawlerVO crawlerVO)
    {
        DocumentRawEntity documentRaw = new DocumentRawEntity();

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
