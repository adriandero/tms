import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpErrorResponse, HttpResponse
} from '@angular/common/http';
import {catchError, from, Observable, pipe, Subscription, switchMap, throwError} from 'rxjs';
import {AuthService} from "../services/auth.service";
import {Router} from '@angular/router';
import {environment} from "../../../environments/environment";
import {map} from "rxjs/operators";
import {stringify} from "querystring";

@Injectable()
export class UnauthorizedInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private router: Router) {
  }

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError(error => {
        if (error instanceof HttpErrorResponse && !request.url.includes('auth/login') && error.status === 401) {
          console.log("401 unauthorized INTERCEPT")
          this.handle401Error(request, next);
        }
        return throwError(error);
      }));
    /* return  next.handle(request).pipe(
       catchError( err => {
         if (err.status !== 200) {
           console.log("refresh request")
           //from(this.handleawait(request, next))
             return this.handleawait(request, next);

           console.log("skip handle await")

         }
         if (!environment.production) {
           console.error(err);
         }
         const error = (err && err.error && err.error.message) || err.statusText;
         return throwError(error);
       })
     );*/
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
console.log("here in handle401error")
    return this.authService.refreshToken().subscribe(data => {
      console.log("here in subscription")
      console.log("status: " + data.statusCode)
      if (data.statusCodeValue === 200 && data.body != null) {
        this.successRefresh(data.body)
      } else {
        this.failRefresh()
      }

    }),
      catchError((err) => {
        return throwError(err);
      })
    }






  private successRefresh(data: RefreshRes) {
    console.log("refresh token  SUCCESS: " + JSON.stringify(data));
    localStorage.setItem('access_token', data.accessToken);
    localStorage.setItem('refresh_token', data.refreshToken);
    localStorage.setItem('login-event', 'login' + Math.random());
    this.router.navigate(['/home']); //TODO navigate to last url
  }

  private failRefresh() {
    console.log("refresh token  FAIL ");
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  /*private handleawait(request: HttpRequest<any>, next: HttpHandler) {

    return this.authService.refreshToken().pipe(
      map(data => {
        if (data.statusCodeValue != 200) {
          console.log("request failed");
          this.authService.clearLocalStorage();
          this.router.navigate(['/', 'login']);
        } else {

          return next.handle(request);
        }
      }))
  }*/


}

interface RefreshRes {
    accessToken: string,
    refreshToken: string,

}
