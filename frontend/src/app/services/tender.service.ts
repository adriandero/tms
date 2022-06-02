import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, retry, throwError } from 'rxjs';
import {environment} from "../../environments/environment";
import { MatSnackBar } from '@angular/material/snack-bar';
import {Chip} from "@material-ui/core";
import {Filter} from "../model/Tender";

@Injectable({
  providedIn: 'root',
})
export class TenderService {

  constructor(private http: HttpClient, public snackBar: MatSnackBar) {}

  public getTenders() {
    return this.http
    .get(`${environment.apiUrl}tenders`, {
      responseType: 'json',
    })
    .pipe(
      retry(3),
      catchError((response) => this.handleError(response))
    );; // This weird thing with creating a function first needs to be done cause otherwise typescript is weird...
  }

  public getAssignments(id: number) {
    return this.http
      .get(`${environment.apiUrl}assignments/tender/${id}`, {
        responseType: 'json',
      })
      .pipe(
        retry(3),
        catchError((response) => this.handleError(response))
      ); // This weird thing with creating a function first needs to be done cause otherwise typescript is weird...
  }

  public getTendersWithCondition(filter :string  ) {
    let filterObj: Filter = JSON.parse(filter)
    console.log("http filter call:" + filter)

    let i = this.http //TODO add conditions
      .get(this.apiUrl + 'tenders', {
        responseType: 'json',
      })
      .pipe(
        retry(3),
        catchError((response) => this.handleError(response))
      );
    console.log(i);
    return i; // This weird thing with creating a function first needs to be done cause otherwise typescript is weird...
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
      this.snackBar.open('API-call failed: Client error', 'Retry', {
        duration: 2000,
      });
    } else if (error.status == 404) {
      console.error('An error occurred:', error.error);
      this.snackBar.open('API-call failed: URL not found', 'Retry', {
        duration: 2000,
      });
    } else if (error.status == 401) {
      console.error('the user is not authenticated');
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      this.snackBar.open('An error occured.', 'Retry', {
        duration: 2000,
      });
      console.error(
        `Backend returned code ${error.status}, body was: `,
        error.error
      );
    }
    // Return an observable with a user-facing error message.
    return throwError(
      () => new Error('Something bad happened; please try again later.')
    );
  }
}
