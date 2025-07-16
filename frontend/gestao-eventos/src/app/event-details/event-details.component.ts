import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EventoService } from '../evento.service';
import { EventModel } from '../core/event.model';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.scss']
})
export class EventDetailsComponent implements OnInit {

  evento?: EventModel;
  carregando = true;
  erro?: string;

  constructor(
    private route: ActivatedRoute,
    private eventoService: EventoService
  ) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.eventoService.buscarPorId(id).subscribe({
      next: evento => {
        this.evento = evento;
        this.carregando = false;
      },
      error: () => {
        this.erro = 'Evento não encontrado.';
        this.carregando = false;
      }
    });
  }
}
