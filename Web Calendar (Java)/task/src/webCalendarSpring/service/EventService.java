package webCalendarSpring.service;

import jakarta.persistence.EntityNotFoundException;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webCalendarSpring.dto.EventDto;
import webCalendarSpring.repository.EventRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventDto getEventDtoById(Integer id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
    }

    public List<EventDto> getEventsByDateRange(LocalDate startTime, LocalDate endTime) {
        return eventRepository.findEventDtosByDateTimeBetween(startTime, endTime);
    }

    public List<EventDto> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<EventDto> getTodaysEvents() {
        return eventRepository.findEventDtosByDateTime(LocalDate.now(ZoneId.of("UTC-6")));
    }

    public EventDto postEventDto(String event, LocalDate date) {
        EventDto eventDto = new EventDto(event, date);
        return eventRepository.save(eventDto);
    }

    public EventDto deleteEventDtoById(Integer id) {
        EventDto eventToDelete = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
        eventRepository.deleteById(id);
        return eventToDelete;
    }
}
