import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tender } from '../model/Tender';


@Injectable({
  providedIn: 'root'
})
export class TenderService {
  private apiUrl = "/api"
  constructor(private http: HttpClient) { }

  getTenders():Observable<Tender[]> {

    return this.http.get<Tender[]>(this.apiUrl + "/tenders");

  }
  
}
