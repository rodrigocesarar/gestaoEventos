package br.com.gestao.eventos.repository;

import br.com.gestao.eventos.entity.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    // Retorna página de eventos não deletados
    Page<Evento> findByDeletadoFalse(Pageable pageable);

    // Retorna evento específico se não estiver deletado
    Optional<Evento> findByIdAndDeletadodFalse(Long id);
}
