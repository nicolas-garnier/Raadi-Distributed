package Raadi.retroindex.application;

import Raadi.framework.RaadiFW;


public class App 
{
    public static void main(String[] args)
    {
        RaadiFW raadiFW = new RaadiFW();

        //raadiFW.bean();
        /*
        manager.execute("https://news.ycombinator.com", 10);

        QueryService query = new QueryService("moored");
        query.tokenisation();

        for (String url : query.getQueryDocuments().keySet())
        {
            System.out.println(url);
        }
        */

        System.out.println( "Hello World!" );
    }
}
