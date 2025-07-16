package br.com.gestao.eventos.service;

import br.com.gestao.eventos.dto.EventRequest;
import br.com.gestao.eventos.dto.EventResponse;
import br.com.gestao.eventos.entity.Event;
import br.com.gestao.eventos.exception.ResourceNotFoundException;
import br.com.gestao.eventos.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventoRepository;

    public Page<EventResponse> listAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateTime").descending());
        Page<Event> eventos = eventoRepository.findByDeletedFalse(pageable);

        return eventos.map(this::toResponse);
    }

    public EventResponse getById(Long id) {
        Event event = findEventOrThrow(id);
        return toResponse(event);
    }

    public EventResponse create(EventRequest request) {
        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dateTime(request.getDateTime())
                .location(request.getLocation())
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .build();

        eventoRepository.save(event);
        return toResponse(event);
    }

    public EventResponse update(Long id, EventRequest request) {
        Event event = findEventOrThrow(id);
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setDateTime(request.getDateTime());
        event.setLocation(request.getLocation());
        event.setUpdatedAt(LocalDateTime.now());

        eventoRepository.save(event);
        return toResponse(event);
    }

    public void softDelwte(Long id) {
        Event event = findEventOrThrow(id);
        event.setDeleted(true);
        eventoRepository.save(event);
    }

    private Event findEventOrThrow(Long id) {
        return eventoRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + id));
    }

    private EventResponse toResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .dateTime(event.getDateTime())
                .location(event.getLocation())
                .build();
    }
}
