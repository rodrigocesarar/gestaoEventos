package br.com.gestao.eventos.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventoRequest {
    @NotBlank
    @Size(max = 100)
    private String titulo;

    @Size(max = 1000)
    private String descricao;

    @FutureOrPresent
    private LocalDateTime dataHora;

    @Size(max = 200)
    private String local;
}
