import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EventoService } from '../evento.service';

@Component({
  selector: 'app-event-form',
  templateUrl: './event-form.component.html',
  styleUrls: ['./event-form.component.scss']
})
export class EventFormComponent implements OnInit {
  eventoForm!: FormGroup;
  idEvento?: number;
  editando = false;
  message = '';
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private eventoService: EventoService
  ) {}

  ngOnInit(): void {
    this.eventoForm = new FormGroup({
      title: new FormControl('', [Validators.required, Validators.maxLength(100)]),
      description: new FormControl('', [Validators.required, Validators.maxLength(1000)]),
      dateTime: new FormControl('', Validators.required),
      location: new FormControl('', [Validators.required, Validators.maxLength(200)]),
    });

    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam && idParam !== 'new') {
        this.idEvento = +idParam;
        this.editando = true;
        this.eventoService.buscarPorId(this.idEvento).subscribe(evento => {
          this.eventoForm.patchValue({
            title: evento.title,
            description: evento.description,
            dateTime: this.formatarDataHora(evento.dateTime),
            location: evento.location
          });
        });
      }
    });
  }

  formatarDataHora(data: string | Date): string {
    const d = new Date(data);
    const pad = (n: number) => n.toString().padStart(2, '0');
    const ano = d.getFullYear();
    const mes = pad(d.getMonth() + 1);
    const dia = pad(d.getDate());
    const hora = pad(d.getHours());
    const min = pad(d.getMinutes());
    return `${ano}-${mes}-${dia}T${hora}:${min}`;
  }

  onSubmit() {
    if (this.eventoForm.valid) {
      const dados = this.eventoForm.value;

      if (this.editando && this.idEvento) {
        this.eventoService.atualizar(this.idEvento, dados)
          .subscribe({
            next: (res) => {
              this.message = 'Evento salvo com sucesso!';
              this.errorMessage = '';
            },
            error: (err) => {
              const errorsObj = err.error;
              const mensagens = Object.entries(errorsObj).map(
                ([campo, mensagem]) => `${campo}: ${mensagem}`
              );
              this.errorMessage = mensagens.join('\n');
            }          
          });
      } else {
        this.eventoService.criar(dados)
        .subscribe({
          next: (res) => {
            this.message = 'Evento salvo com sucesso!';
            this.errorMessage = '';
          },
          error: (err) => {
            const errorsObj = err.error;
            const mensagens = Object.entries(errorsObj).map(
              ([campo, mensagem]) => `${campo}: ${mensagem}`
            );
            this.errorMessage = mensagens.join('\n');
            this.message = '';
          }          
        });
      }
    } else {
      this.eventoForm.markAllAsTouched();
    }
  }

}
