import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';

@Injectable()
export class ApiService {

  constructor(private http: HttpClient) { }
  baseUrl = 'http://localhost:8080/certificates';

  login(loginPayload) {
    const headers = {
      Authorization: 'Basic ' + btoa('client-id:client-id'),
      'Content-type': 'application/x-www-form-urlencoded'
    };
    return this.http.post('http://localhost:8081/' + 'oauth/token', loginPayload, {headers});
  }
}
