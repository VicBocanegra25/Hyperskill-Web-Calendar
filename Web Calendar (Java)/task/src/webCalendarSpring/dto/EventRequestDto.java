package webCalendarSpring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public class EventRequestDto {
    @NotBlank
    @JsonProperty("event")
    private String event;

    @NotNull
    @JsonProperty("date")
    private LocalDate date;

    public EventRequestDto() {
    }

    public EventRequestDto(String event, LocalDate date) {
        this.event = event;
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
