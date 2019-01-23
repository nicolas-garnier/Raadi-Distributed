package Raadi.domain;

import Raadi.domain.model.DocumentRaw;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.HttpStatusException;

import java.util.HashSet;

public final class Crawler
{
    public static DocumentRaw crawl(String URL)
    {
        DocumentRaw documentRaw = new DocumentRaw();

        try {
            // Get the content of the page
            Document doc = Jsoup.connect(URL).get();
            Element content = doc.body();
            String contentRaw = content.text().toLowerCase();

            // Get all the url linked in the page
            Elements links = doc.select("a[href]");
            HashSet<String> childrenURL = new HashSet<>();
            for (Element link : links) {
                childrenURL.add(link.attr("abs:href"));
            }

            // Fill the document with several informations : url, content, children urls
            documentRaw.setContent(contentRaw);
            documentRaw.setURL(URL);
            documentRaw.setChildrenURL(childrenURL);

        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }

        return documentRaw;
    };
}
