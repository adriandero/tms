import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {catchError, from, Observable, throwError} from 'rxjs';
import {AuthService} from "../services/auth.service";
import { Router } from '@angular/router';
import {environment} from "../../../environments/environment";

@Injectable()
export class UnauthorizedInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private router: Router) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return  next.handle(request).pipe( catchError( (err) => {
        if (err.status !== 200) {
          console.log("refresh request")
          from(this.handleawait(request, next))
        }


        if (!environment.production) {
          console.error(err);
        }
        const error = (err && err.error && err.error.message) || err.statusText;
        return throwError(error);
      })
    );
  }
  private async handleawait(request: HttpRequest<any>, next: HttpHandler) {

    const newRequest =   await this.authService.refreshToken()
    newRequest.subscribe(data => {
      if (data.statusCodeValue != 200){
        console.log("request failed")
        this.authService.clearLocalStorage()
        this.router.navigate(['/','login']);
      }
      else{
            console.log("refresh token response ")
           localStorage.setItem('access_token', data.body.accessToken);
           localStorage.setItem('refresh_token',data.body.refreshToken);
         localStorage.setItem('login-event', 'login' + Math.random());
      }
    })

    return next.handle(request).toPromise();
  }

}
