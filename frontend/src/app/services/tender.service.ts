import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, retry, throwError } from 'rxjs';
import { Tender } from '../model/Tender';
import { MatSnackBar } from '@angular/material/snack-bar';
import { A } from '@angular/cdk/keycodes';

@Injectable({
  providedIn: 'root'
})
export class TenderService {
  private apiUrl = "/api/"

  constructor(private http: HttpClient,public snackBar: MatSnackBar) {}

  public getTenders() {
    return this.http.get(this.apiUrl + "tenders", {
      responseType: 'json'
    }).pipe(retry(3),
      catchError((response) => this.handleError(response))); // This weird thing with creating a function first needs to be done cause otherwise typescript is weird...
  }
  
  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
      this.snackBar.open("API-call failed: Client error", "Retry", {
        duration: 2000
      });
    } else if (error.status == 404){
      console.error('An error occurred:', error.error);
      this.snackBar.open("API-call failed: URL not found", "Retry", {
        duration: 2000
      });
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      this.snackBar.open("An error occured.", "Retry", {
        duration: 2000
      });
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }
  
}
