package webCalendarSpring.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import webCalendarSpring.dto.EventDto;
import webCalendarSpring.dto.EventRequestDto;
import webCalendarSpring.service.EventService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/event")
public class WebCalendarController {

    private final EventService eventService;

    @Autowired
    public WebCalendarController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> events = eventService.getAllEvents();
        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(events);
    }

    @GetMapping("/today")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<EventDto>> getToday() {
        List<EventDto> events = eventService.getTodaysEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable("id") Integer id) {
        EventDto event = eventService.getEventDtoById(id);
        if (event == null) {
            return new ResponseEntity<>("The event doesn't exist!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping(params = {"start_time", "end_time"})
    // We use @RequestParam for query parameters?key=value
    public ResponseEntity<List<EventDto>> getEventsByDateRange(
            @RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate startTime,
            @RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate endTime) {
        List<EventDto> events = eventService.getEventsByDateRange(startTime, endTime);

        if (events.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.ok(events);
    }

    @PostMapping()
    public ResponseEntity<Map<String, String>> postEvent(@Valid @RequestBody EventRequestDto eventRequestDto) {
        EventDto savedEvent = eventService.postEventDto(eventRequestDto.getEvent(), eventRequestDto.getDate());

        Map<String, String> response = new HashMap<>();
        response.put("message", "The event has been added!");
        response.put("event", savedEvent.getEvent());
        response.put("date", savedEvent.getDateTime().toString());

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEventById(@PathVariable("id") Integer id) {
        try {
            EventDto event = eventService.deleteEventDtoById(id);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "The event doesn't exist!");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", "The event doesn't exist!");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
