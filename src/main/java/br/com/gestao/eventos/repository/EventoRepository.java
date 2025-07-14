package br.com.gestao.eventos.repository;

import br.com.gestao.eventos.entity.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    Page<Evento> findByDeletadoFalse(Pageable pageable);

    Optional<Evento> findByIdAndDeletadoFalse(Long id);
}
