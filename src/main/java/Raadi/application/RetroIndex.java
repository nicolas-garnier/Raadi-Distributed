package Raadi.application;

import Raadi.domain.entity.RetroIndexEntity;
import Raadi.domain.service.RetroIndexService;
import Raadi.framework.RaadiFW;

/**
 * Retro indexer application entry point.
 */
public class RetroIndex
{

    /**
     * Retro indexer application's main function.
     * @param args Arguments for main function.
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {
        final RaadiFW raadi = new RaadiFW();

        raadi.bean(RetroIndexEntity.class, new RetroIndexEntity());
        RetroIndexEntity retroIndexEntity = (RetroIndexEntity) raadi.instanceOf(RetroIndexEntity.class);

        raadi.bean(RetroIndexService.class, new RetroIndexService(retroIndexEntity));
        RetroIndexService retroIndexService = (RetroIndexService) raadi.instanceOf(RetroIndexService.class);

        retroIndexService.start();
        System.out.println( "RetroIndex started");
    }
}