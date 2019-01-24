package Raadi.application;

import Raadi.domain.service.RetroIndexService;
import Raadi.framework.RaadiFW;


public class RetroIndex
{
    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {
        final RaadiFW raadi = new RaadiFW();

        raadi.bean(RetroIndexService.class, new RetroIndex());
        RetroIndexService retroIndexService = (RetroIndexService) raadi.instanceOf(RetroIndexService.class);

        retroIndexService.start();
        System.out.println( "RetroIndex started" );
    }
}
