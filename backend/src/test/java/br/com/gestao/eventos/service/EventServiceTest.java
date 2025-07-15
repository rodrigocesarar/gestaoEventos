package br.com.gestao.eventos.service;

import br.com.gestao.eventos.dto.EventRequest;
import br.com.gestao.eventos.dto.EventResponse;
import br.com.gestao.eventos.entity.Event;
import br.com.gestao.eventos.exception.ResourceNotFoundException;
import br.com.gestao.eventos.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void shouldCreateEventSuccessfully() {
        EventRequest request = new EventRequest();
        request.setTitle("Eventoo Teste");
        request.setDescription("Descrição");
        request.setDateTime(LocalDateTime.now().plusDays(1));
        request.setLocation("Local");

        Event savedEvent = Event.builder()
                .id(1L)
                .title(request.getTitle())
                .description(request.getDescription())
                .dateTime(request.getDateTime())
                .location(request.getLocation())
                .deleted(false)
                .build();

        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        EventResponse response = eventService.create(request);

        assertNotNull(response);
        assertEquals("Evento Teste", response.getTitle());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void shouldThrowWhenEventNotFound() {
        when(eventRepository.findByIdAndDeletedFalse(999L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            eventService.getById(999L);
        });
    }

    @Test
    void shouldSoftDeleteEvent() {
        Event event = Event.builder()
                .id(1L)
                .title("Evento")
                .deleted(false)
                .build();

        when(eventRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        eventService.softDelwte(1L);

        assertTrue(event.getDeleted());
        verify(eventRepository, times(1)).save(event);
    }
}
