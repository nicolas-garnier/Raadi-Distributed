package Raadi.indexer.application;

import Raadi.Manager;
import Raadi.retroindex.domain.service.QueryService;

public class App 
{
    public static void main( String[] args ) {
        Manager manager = Manager.getInstance();
        manager.execute("https://news.ycombinator.com", 10);

        /*QueryService query = new QueryService("moored");
        query.tokenization();

        for (String url : query.getQueryDocuments().keySet())
        {
            System.out.println(url);
        }

        System.out.println( "Hello World!" );*/
    }
}
