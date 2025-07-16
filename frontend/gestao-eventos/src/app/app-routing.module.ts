import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { EventFormComponent } from './event-form/event-form.component';
import { EventListComponent } from './event-list/event-list.component';
import { EventDetailsComponent } from './event-details/event-details.component';

const routes: Routes = [
  { path: 'events/new', component: EventFormComponent },
  { path: 'events/:id/edit', component: EventFormComponent },
  { path: 'events/:id', component: EventDetailsComponent },
  { path: 'events', component: EventListComponent },
  { path: '', redirectTo: '/events', pathMatch: 'full' },
  { path: '**', redirectTo: '/events' }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
