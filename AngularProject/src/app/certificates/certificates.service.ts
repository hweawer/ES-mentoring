import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Certificate} from '../certificate';

@Injectable({
  providedIn: 'root'
})
export class CertificatesService {
  private BASE_URL = 'http://localhost:8080/certificates';

  constructor(private http: HttpClient) { }

  static createDefaultHeaders() {
    return new HttpHeaders()
      .set('Content-type', 'application/json');
  }

  public getCertificates(page?: number, limit?: number) {
    if (page === undefined) { page = 1; }
    if (limit === undefined) { limit = 5; }
    return this.http.get(this.BASE_URL + '?page=' + page + '&limit=' + limit);
  }

  public getCertificateById(id: number) {
    return this.http.get(this.BASE_URL + '/' + id);
  }

  public createCertificate(certificate: Certificate, headers?: HttpHeaders) {
    if (headers === undefined) { headers = CertificatesService.createDefaultHeaders(); }
    return this.http.post(this.BASE_URL, certificate, {headers});
  }

  public updateCertificate(certificate: Certificate, headers?: HttpHeaders) {
    if (headers === undefined) { headers = CertificatesService.createDefaultHeaders(); }
    return this.http.put(this.BASE_URL + '/' + certificate.id, certificate, {headers});
  }

  public deleteCertificate(certificate: Certificate, headers?: HttpHeaders) {
    if (headers === undefined) { headers = CertificatesService.createDefaultHeaders(); }
    return this.http.delete(this.BASE_URL + '/' + certificate.id, {headers});
  }
}
