package Raadi.domain.entity;

import javafx.util.Pair;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Event store entity class.
 */
public class EventStoreEntity
{
    /**
     * Attributes.
     */
    private ArrayList<Pair<String, Type>> eventStore;

    /**
     * Event store entity class.
     */
    public EventStoreEntity() {
        this.eventStore = new ArrayList<>();
    }

    /**
     * Getter for eventStore array list.
     * @return The event store array list.
     */
    public ArrayList<Pair<String, Type>> getEventStore() {
        return eventStore;
    }

    /**
     * Setter for eventStore array list.
     * @param eventStore The event store array list.
     */
    public void setEventStore(ArrayList<Pair<String, Type>> eventStore) {
        this.eventStore = eventStore;
    }
}
