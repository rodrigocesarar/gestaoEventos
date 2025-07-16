import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EventModel } from './core/event.model';
import { Page } from './core/page.model';

@Injectable({
  providedIn: 'root'
})
export class EventoService {

  private apiUrl = 'http://localhost:8080/api/events';

  constructor(private http: HttpClient) { }

  listar(page: number, size: number): Observable<Page<EventModel>> {
    return this.http.get<Page<EventModel>>(`http://localhost:8080/api/events?page=${page}&size=${size}`);
  }

  buscarPorId(id: number): Observable<EventModel> {
    return this.http.get<EventModel>(`${this.apiUrl}/${id}`);
  }

  criar(evento: EventModel): Observable<EventModel> {
    return this.http.post<EventModel>(this.apiUrl, evento);
  }

  atualizar(id: number, evento: EventModel): Observable<EventModel> {
    return this.http.put<EventModel>(`${this.apiUrl}/${id}`, evento);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
