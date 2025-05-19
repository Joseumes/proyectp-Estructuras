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
  imagenes: string[] = [];
  mensaje: string = '';

  constructor(private http: HttpClient) {}

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onUpload() {
    if (!this.selectedFile) return;
    this.imagenes = []; // limpia las imágenes anteriores
    this.mensaje = 'hola'; // limpia el mensaje anterior

    const formData = new FormData();
    formData.append('file', this.selectedFile);

    this.http.post<string[]>('http://localhost:8081/api/estructuras/procesar', formData)
      .subscribe({
        next: (rutas) => {
          this.imagenes = rutas.map(r => 'http://localhost:8081/' + r);
          this.mensaje = 'Visualización generada';
        },
        error: (error) => {
          this.mensaje = 'Error al procesar: ' + (error.error || 'Error desconocido');
        }
      });
  }
}
