import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CertificatesService {
  private baseUrl = 'http://localhost:8080/certificates';

  constructor(private http: HttpClient) { }

  getCertificates(page: number, limit: number) {
    return this.http.get(this.baseUrl + '?page=' + page + '&limit=' + limit);
  }
}
