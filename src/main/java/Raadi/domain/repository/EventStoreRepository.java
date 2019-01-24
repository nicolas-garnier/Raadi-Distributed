package Raadi.domain.repository;

import Raadi.domain.entity.EventStoreEntity;
import javafx.util.Pair;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EventStoreRepository implements EventStoreRepositoryInterface {

    private EventStoreEntity eventStoreEntity;

    public EventStoreRepository(EventStoreEntity eventStoreEntity) {
        this.eventStoreEntity = eventStoreEntity;
    }

    @Override
    public void insert(String value, Type type) {
        eventStoreEntity.getEventStore().add(new Pair<>(value, type));
    }

    @Override
    public ArrayList<Pair<String, Type>> getAll() {
        return eventStoreEntity.getEventStore();
    }
}
