import { provideRouter } from '@angular/router';
import { HomeComponent } from './pages/home.component';

export const appConfig = {
  providers: [
    provideRouter([
      { path: '', component: HomeComponent }
    ])
  ]
};
