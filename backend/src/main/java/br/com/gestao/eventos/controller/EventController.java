package br.com.gestao.eventos.controller;

import br.com.gestao.eventos.dto.EventRequest;
import br.com.gestao.eventos.dto.EventResponse;
import br.com.gestao.eventos.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/events")
@RequiredArgsConstructor
@Tag(name = "Eventos", description = "Operações com eventos")
@CrossOrigin(origins = "http://localhost:4200")
public class EventController {

    private final EventService eventService;

    @GetMapping
    @Operation(summary = "Listar eventos", description = "Retorna uma lista paginada de eventos")
    public ResponseEntity<Page<EventResponse>> listAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<EventResponse> events = eventService.listAll(page, size);

        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar evento por ID")
    public ResponseEntity<EventResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Criar novo evento")
    public ResponseEntity<EventResponse> create(@RequestBody @Valid EventRequest request) {
        return ResponseEntity.ok(eventService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um evento existente")
    public ResponseEntity<EventResponse> update(
        @PathVariable Long id,
        @RequestBody @Valid EventRequest request
    ) {
        return ResponseEntity.ok(eventService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um evento existente")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

}
