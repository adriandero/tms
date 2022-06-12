import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {catchError, Observable, retry, throwError} from 'rxjs';
import {environment} from "../../environments/environment";
import { MatSnackBar } from '@angular/material/snack-bar';
import {Chip} from "@material-ui/core";
import {AssignedIntStatus, Assignment, Filter, Tender} from "../model/Tender";
import {BackendResponse} from "../model/protocol/Response";
import {RefreshRes, User} from "../auth";

@Injectable({
  providedIn: 'root',
})
export class TenderService {

  constructor(private http: HttpClient, public snackBar: MatSnackBar) {}

  public getAssignments(id: number){
    console.log("getAssignments")
    return this.http
      .get<BackendResponse<Assignment>>(`${environment.apiUrl}assignments/tender/${id}`, {
        responseType: 'json',
      })
      .pipe(
        catchError((response) => this.handleError(response))
      ); // This weird thing with creating a function first needs to be done cause otherwise typescript is weird...
  }

  getUser(username: string) {
    console.log("getUser")
    return this.http.get<BackendResponse<User>>(`${environment.apiUrl}users/?id=${username}`,{
      responseType: 'json',
    })
      .pipe(
        catchError((response) => this.handleError(response))
      );
  }
  public getTenders(filter: Filter) {
    return this.http
      .post<BackendResponse<Tender[]>>(`${environment.apiUrl}tenders`, filter, {
        responseType: 'json',
      })
      .pipe(
        retry(0),
        catchError((response) => this.handleError(response))
      );
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
