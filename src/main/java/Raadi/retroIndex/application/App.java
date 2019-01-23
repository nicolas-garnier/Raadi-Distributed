package Raadi.retroIndex.application;

import Raadi.Manager;
import Raadi.retroIndex.domain.Query;

public class App 
{
    public static void main( String[] args ) {
        Manager manager = Manager.getInstance();
        manager.execute("https://news.ycombinator.com", 10);

        Query query = new Query("moored");
        query.tokenisation();

        for (String url : query.getQueryDocuments().keySet())
        {
            System.out.println(url);
        }

        System.out.println( "Hello World!" );
    }
}
