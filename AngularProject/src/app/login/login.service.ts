import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {TokenStorageService} from '../auth/token-storage.service';

@Injectable()
export class LoginService {

  constructor(private http: HttpClient,
              private tokenStorage: TokenStorageService) { }

  login(loginPayload) {
    const headers = {
      Authorization: 'Basic ' + btoa('client-id:client-id'),
      'Content-type': 'application/x-www-form-urlencoded'
    };
    return this.http.post('http://localhost:8081/' + 'oauth/token', loginPayload, {headers});
  }

  logout() {
    this.tokenStorage.signOut();
  }
}
