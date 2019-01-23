package Raadi.retroindex.controller;

import Raadi.retroindex.domain.service.QueryService;

import static spark.Spark.get;

public class Query {

    public static void main(String[] args) {
        QueryService service = new QueryService();
        get("/", service.setQuery);
    }
}
