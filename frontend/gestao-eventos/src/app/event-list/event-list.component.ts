import { Component, OnInit } from '@angular/core';
import { EventoService } from '../evento.service';
import { EventModel } from '../core/event.model';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html'
})
export class EventListComponent implements OnInit {

  eventos: EventModel[] = [];
  paginaAtual: number = 0; 
  totalPages: number = 0;
  tamanhoPagina: number = 5;

  constructor(private eventoService: EventoService) {}

  ngOnInit() {
    this.carregarEventos(this.paginaAtual);
  }

  carregarEventos(page: number) {
    this.eventoService.listar(page, this.tamanhoPagina).subscribe(data => {
      this.eventos = data.content;
      this.totalPages = data.totalPages;
      this.paginaAtual = data.number;
    });
  }

  proximaPagina() {
    if (this.paginaAtual + 1 < this.totalPages) {
      this.carregarEventos(this.paginaAtual + 1);
    }
  }

  paginaAnterior() {
    if (this.paginaAtual > 0) {
      this.carregarEventos(this.paginaAtual - 1);
    }
  }
}