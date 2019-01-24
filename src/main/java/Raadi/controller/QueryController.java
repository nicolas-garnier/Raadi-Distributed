package Raadi.controller;
import Raadi.domain.service.QueryService;
import Raadi.framework.RaadiFW;

import static spark.Spark.get;
import static spark.Spark.port;

public class QueryController {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        RaadiFW raadiFW = new RaadiFW();

        raadiFW.bean(QueryService.class, new QueryService());
        QueryService queryService = (QueryService) raadiFW.instanceOf(QueryService.class);

        port(4568);
        get("/query", queryService.setQuery);
    }
}
