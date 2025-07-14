package br.com.gestao.eventos.service;

import br.com.gestao.eventos.dto.EventoRequest;
import br.com.gestao.eventos.dto.EventoResponse;
import br.com.gestao.eventos.entity.Evento;
import br.com.gestao.eventos.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    public Page<EventoResponse> listar(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataHora").descending());
        Page<Evento> eventos = eventoRepository.findByDeletadoFalse(pageable);

        return eventos.map(this::toResponse);
    }

    public EventoResponse buscarPorId(Long id) {
        Evento evento = buscarEventoPorId(id);
        return toResponse(evento);
    }

    public EventoResponse salvar(EventoRequest request) {
        Evento evento = Evento.builder()
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .dataHora(request.getDataHora())
                .local(request.getLocal())
                .deletado(false)
                .build();

        eventoRepository.save(evento);
        return toResponse(evento);
    }

    public EventoResponse atualizar(Long id, EventoRequest request) {
        Evento evento = buscarEventoPorId(id);
        evento.setTitulo(request.getTitulo());
        evento.setDescricao(request.getDescricao());
        evento.setDataHora(request.getDataHora());
        evento.setLocal(request.getLocal());

        eventoRepository.save(evento);
        return toResponse(evento);
    }

    public void deletar(Long id) {
        Evento evento = buscarEventoPorId(id);
        evento.setDeletado(true);
        eventoRepository.save(evento);
    }

    private Evento buscarEventoPorId(Long id) {
        return eventoRepository.findByIdAndDeletadoFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + id));
    }

    private EventoResponse toResponse(Evento evento) {
        return EventoResponse.builder()
                .id(evento.getId())
                .titulo(evento.getTitulo())
                .descricao(evento.getDescricao())
                .dataHora(evento.getDataHora())
                .local(evento.getLocal())
                .build();
    }
}
