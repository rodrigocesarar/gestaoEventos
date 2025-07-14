package br.com.gestao.eventos.repository;

import br.com.gestao.eventos.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
