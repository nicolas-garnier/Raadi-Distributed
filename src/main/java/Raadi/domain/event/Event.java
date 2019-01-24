package Raadi.domain.event;

import java.util.Date;

public abstract class Event
{
    private Date dateCreated;

    protected Event()
    {
        this.dateCreated = new Date();
    }
}
