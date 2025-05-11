import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  selectedFile: File | null = null;
  imagenUrl: string | null = null;
  mensaje: string = '';

  constructor(private http: HttpClient) {}

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onUpload() {
    if (!this.selectedFile) return;
  
    const formData = new FormData();
    formData.append('file', this.selectedFile);
  
    this.http.post('http://localhost:8081/api/estructuras/procesar', formData, { responseType: 'text' })
      .subscribe({
        next: (response) => {
          this.mensaje = response;
          const path = response.split('Imagen generada en: ')[1];
          if (path) {
            this.imagenUrl = 'http://localhost:8081/' + path;
          }
        },
        error: (error) => {
          if (error.error instanceof ProgressEvent) {
            this.mensaje = 'Error al conectar con el servidor.';
          } else {
            this.mensaje = 'Error del servidor: ' + (error.error || 'Respuesta no válida');
          }
        }
      });
  }
}  