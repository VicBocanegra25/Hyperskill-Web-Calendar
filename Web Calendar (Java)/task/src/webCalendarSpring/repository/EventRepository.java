package webCalendarSpring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webCalendarSpring.dto.EventDto;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<EventDto, Integer> {

    @Override
    List<EventDto> findAll();

    List<EventDto> findEventDtosByDateTime(LocalDate date);

    List<EventDto> findEventDtosByDateTimeBetween(LocalDate startTime, LocalDate endTime);
}
