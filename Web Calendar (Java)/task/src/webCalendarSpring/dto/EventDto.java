package webCalendarSpring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class EventDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank
    private String event;
    @NotNull
    @JsonProperty("date")
    private LocalDate dateTime;

    public EventDto() {
    }

    public EventDto(Integer id, String event, LocalDate dateTime) {
        this.id = id;
        this.event = event;
        this.dateTime = dateTime;
    }

    public EventDto(String event, LocalDate dateTime) {
        this.dateTime = dateTime;
        this.event = event;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EventDto eventDto = (EventDto) o;
        return Objects.equals(id, eventDto.id) && Objects.equals(event, eventDto.event) && Objects.equals(dateTime, eventDto.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, event, dateTime);
    }
}

