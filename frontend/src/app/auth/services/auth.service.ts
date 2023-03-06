import {Injectable, OnDestroy} from '@angular/core';
import {
  BehaviorSubject,
  catchError,
  delay,
  finalize,
  Observable,
  of,
  Subject,
  Subscription,
  tap,
  throwError
} from "rxjs";
import {Router} from "@angular/router";
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from "@angular/common/http";
import {map} from "rxjs/operators";
import {Role} from "../../model/Roles";
import {User} from "../../model/User";
import {environment} from "../../../environments/environment";
import {decode} from "jsonwebtoken";
import {error} from "@angular/compiler/src/util";
import {BackendResponse} from "../../model/protocol/Response";


interface AuthRes {
  body: {
    "tokens": {
      "accessToken": string,
      "refreshToken": string
    }
    "mail": string
    "roles": Role[]
    "tokenType": string
  }
}

export interface RefreshRes {
  accessToken: string,
  refreshToken: string,
}


@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private readonly apiUrl = `${environment.apiUrl}`;
  private timer: Subscription | undefined;
  private _access_token = new BehaviorSubject<string | null>(null);
  user$ = this._access_token.asObservable();


  constructor(private router: Router, private http: HttpClient) {
  }

  login(mail: string, password: string) {
    console.log("enter login before : ")
    return this.http
      .post<AuthRes>(`${this.apiUrl}auth/login`, {mail, password})
      .pipe(
        catchError((err: HttpErrorResponse) => {
          console.log("error data")
          return throwError(() => new Error("password or username invalid"));
        }),
        map((x) => {
          console.log("set data")
          console.log("refreshtoken" + x.body.tokens.refreshToken)
          this.setLocalStorage(x); //TODO store only tokens
          this.router.navigate(['/login'], {
            queryParams: {returnUrl: this.router.routerState.snapshot.url},
          })

        })
      )

  }


  refreshToken(): Observable<BackendResponse<RefreshRes>> {
    const refreshToken = localStorage.getItem('refresh_token');
    const accessToken = localStorage.getItem('access_token');

    return this.http
      .post<BackendResponse<RefreshRes>>(`${this.apiUrl}auth/refresh`, {
        "accessToken": accessToken,
        "refreshToken": refreshToken
      })


    /*  .pipe(

        map((data: HttpResponse<RefreshRes>) => {
          console.log("get refresh tokens: " + data);
          console.log("refresh token response ");
          localStorage.setItem('access_token', data.body.accessToken);
          localStorage.setItem('refresh_token', data.body.refreshToken);
          localStorage.setItem('login-event', 'login' + Math.random());
          return data;
        }),
        catchError( err => {

        }));*/


    // pipe(
    // map((x) => {
    //   console.log("statuscodevalue:    " + JSON.stringify(x))
    //   if (x.statusCodeValue == 200) {
    //     console.log("refresh token response " + x)
    //     localStorage.setItem('access_token', x.body.accessToken);
    //     localStorage.setItem('refresh_token', x.body.refreshToken);
    //     localStorage.setItem('login-event', 'login' + Math.random());
    //   }
    //   status = x.statusCodeValue
    //   return status;
    // }))
    /*    map((x) => {
              console.log("statuscodevalue:    " + x.statusCodeValue)
              if (x.statusCodeValue == 200) {
                console.log("refresh token response " + x)
                localStorage.setItem('access_token', x.body.accessToken);
                localStorage.setItem('refresh_token', x.body.refreshToken);
                localStorage.setItem('login-event', 'login' + Math.random());
                success = true;
              }
              return x;
            })
          );*/

  }



  setLocalStorage(x: AuthRes) {
    console.log("set local storage")
    localStorage.setItem('access_token', x.body.tokens.accessToken);
    localStorage.setItem('refresh_token', x.body.tokens.refreshToken);
    localStorage.setItem('login-event', 'login' + Math.random());
  }

  clearLocalStorage() {
    console.log("storage cleared")
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    localStorage.setItem('logout-event', 'logout' + Math.random());
  }

  tokenIsValid(): Observable<boolean> {
    const accessToken = localStorage.getItem("access_token");
    const refreshToken = localStorage.getItem("refresh_token");
    let status = 0;
    const subject = new Subject<boolean>();

    return this.http
      .post(`${this.apiUrl}auth/validate`, {
        "accessToken": accessToken,
        "refreshToken": refreshToken
      }, {observe: 'response'})
      .pipe(
        map(data => {
            return data.status === 200 ? true : false;
          }
        )
      );
    //console.log(subject)
    //return subject.asObservable();
    /*    map((x) => {
    /* return this.http
     .post<RefreshRes>(`${this.apiUrl}auth/refresh`, {refreshToken, accessToken})
     .pipe(
       map((x) => {
         if (x.statusCodeValue == 400) {
           this.router.navigate(["/login"])
           return false;
         } else {
           console.log("refresh token response " + x)
           localStorage.setItem('access_token', x.body.accessToken);
           localStorage.setItem('refresh_token', x.body.refreshToken);
           localStorage.setItem('login-event', 'login' + Math.random());
           return true;
         }
       }));*/
  }


}
