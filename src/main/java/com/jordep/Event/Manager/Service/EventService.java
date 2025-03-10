package com.jordep.Event.Manager.Service;

import com.jordep.Event.Manager.Model.Event;
import com.jordep.Event.Manager.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event addNewEvent(Event event) {
        event.setPrettyName(event.getTitle().toLowerCase().replaceAll(" ", "-"));
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return (List<Event>) eventRepository.findAll(); // (List<Event>) converte para o tipo Event, pois ele esta retornando do tipo EventRepository
    }

    public Event getByPrettyName(String prettyName) {
        return eventRepository.findByPrettyName(prettyName);
    }

}
