package Raadi.retroindex.application;

import Raadi.retroindex.domain.service.RetroIndexService;


public class App 
{
    public static void main(String[] args)
    {
        System.out.println( "RetroIndex started" );

        RetroIndexService.getInstance().start();
    }
}
