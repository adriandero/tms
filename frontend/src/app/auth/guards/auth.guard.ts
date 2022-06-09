import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {catchError, finalize, Observable, of, tap} from 'rxjs';
import {map} from "rxjs/operators";
import {AuthService} from "../services/auth.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private authService: AuthService) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    Observable < boolean > | Promise < boolean > | boolean  {
console.log("hello im here 2")
    return this.authService.tokenIsValid().pipe(
      tap( data => {
        if(!data){
          this.router.navigate(['login']);
        }
      }),
    catchError( err =>{
      console.log("hello im here")
      this.router.navigate(['login']);
      return of(false);
    }))

    /* if(this.authService.tokenIsValid().subscribe((item) => item === 200)){
       console.log("token is valid : true")
      return true;
    } else {
       console.log("token is valid : false")
       this.router.navigate(['login'], {
        queryParams: {returnUrl: state.url},
      });
      return false;
    }*/
  }


 /*   return this.authService.user$.pipe(
      map(() => {
        if (true) { //TODO
          return true;
        } else {
          this.router.navigate(['login'], {
            queryParams: { returnUrl: state.url },
          });
          return false;
        }
      })
    );*/
  }


