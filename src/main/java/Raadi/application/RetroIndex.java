package Raadi.application;

import Raadi.domain.service.RetroIndexService;


public class RetroIndex
{
    public static void main(String[] args)
    {
        System.out.println( "RetroIndex started" );

        RetroIndexService.getInstance().start();
    }
}
