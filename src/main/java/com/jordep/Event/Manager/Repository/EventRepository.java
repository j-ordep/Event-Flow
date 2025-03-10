package com.jordep.Event.Manager.Repository;

import com.jordep.Event.Manager.Model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
    Event findByPrettyName(String prettyName);
}
