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
  imagenes: { src: string, tipo: string }[] = [];
  mensaje: string = '';

  constructor(private http: HttpClient) {}

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onUpload() {
    if (!this.selectedFile) return;

    this.imagenes = [];
    this.mensaje = '';

    const formData = new FormData();
    formData.append('file', this.selectedFile);

    this.http.post<string[]>('http://localhost:8081/api/estructuras/procesar', formData)
      .subscribe({
        next: (rutas) => {
          this.mensaje = 'Visualización generada correctamente.';

          // 💡 Extrae tipo desde la ruta: imagenes/pila_xxx.png
          this.imagenes = rutas.map(ruta => {
            const tipo = ruta.split('/')[1].split('_')[0]; // pila, cola, etc.
            return {
              src: 'http://localhost:8081/' + ruta,
              tipo
            };
          });
        },
        error: (error) => {
          this.mensaje = 'Error al procesar: ' + (error.error || 'Error desconocido');
        }
      });
  }
}
