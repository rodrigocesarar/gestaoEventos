package br.com.gestao.eventos.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EventoResponse {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataHora;
    private String local;
}
