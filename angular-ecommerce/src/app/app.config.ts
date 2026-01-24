import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { routes } from './app.routes';
import { ProductService } from './services/product.service';
import { provideAuth0 } from '@auth0/auth0-angular';
import myAppConfig from './config/my-app-config';
import { authInterceptor } from './services/auth-interceptor.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withInterceptors([authInterceptor])),
    ProductService,
    provideAuth0(myAppConfig.auth0)
  ]
};