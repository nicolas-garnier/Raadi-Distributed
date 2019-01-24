package Raadi.domain.repository;

import javafx.util.Pair;

import java.lang.reflect.Type;
import java.util.ArrayList;

interface EventStoreRepositoryInterface {

    void insert(String value, Type type);
    ArrayList<Pair<String, Type>> getAll();
}
