package Raadi.domain.entity;

import javafx.util.Pair;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EventStoreEntity
{
    private ArrayList<Pair<String, Type>> eventStore;

    public EventStoreEntity() {
        this.eventStore = new ArrayList<>();
    }

    public ArrayList<Pair<String, Type>> getEventStore() {
        return eventStore;
    }

    public void setEventStore(ArrayList<Pair<String, Type>> eventStore) {
        this.eventStore = eventStore;
    }
}
