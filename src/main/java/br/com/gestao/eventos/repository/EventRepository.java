package br.com.gestao.eventos.repository;

import br.com.gestao.eventos.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByDeletedFalse(Pageable pageable);

    Optional<Event> findByIdAndDeletedFalse(Long id);
}
