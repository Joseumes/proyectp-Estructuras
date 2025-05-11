// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home.component';
import { EstructurasComponent } from './pages/estructuras.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'estructuras', component: EstructurasComponent },
];
