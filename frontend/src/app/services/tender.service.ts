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
  private apiUrl = "https://6256e55fe07d2c9a670eea1d.mockapi.io/api/sampleTender"

  constructor(private http: HttpClient,public snackBar: MatSnackBar) { }

  getTenders() {
  
    return  this.http.get(this.apiUrl, {
      responseType: 'json'
    }).pipe(retry(3),
      catchError(this.handleError));
   
      
  }
  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
      this.snackBar.open("API-call failed: Client error","Undo",{
        duration: 20
      });
    } 
    
    else if (error.status == 404){
      console.error('An error occurred:', error.error);
      this.snackBar.open("API-call failed: URL not found","Undo",{
        duration: 20
      });
    }
    else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      this.snackBar.open("API call failed","Undo",{
        duration: 20
      });
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }
  
}
