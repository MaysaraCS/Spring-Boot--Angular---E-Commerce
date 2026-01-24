import { HttpEvent, HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { Observable, switchMap } from 'rxjs';
import { Api_URL } from '../app.constants';

export const authInterceptor = (req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> => {
  const auth = inject(AuthService);
  // Only add an access token for secured endpoints
  const securedEndpoints = ['/orders'];

  if (securedEndpoints.some(url => req.url.includes(url))) {
    return auth.getAccessTokenSilently().pipe(
      switchMap(accessToken => {
        // Clone the request and add new header with access token
        const authReq = req.clone({
          setHeaders: {
            Authorization: 'Bearer ' + accessToken
          }
        });
        return next(authReq);
      })
    );
  }

  return next(req);
};