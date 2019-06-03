import { Injectable } from '@angular/core';

export const TOKEN_KEY = 'AuthToken';
export const USERNAME_KEY = 'AuthUsername';
export const AUTHORITIES_KEY = 'AuthAuthorities';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  constructor() { }

  public signOut() {
    window.sessionStorage.clear();
  }

  public saveToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUsername(username: string) {
    window.sessionStorage.removeItem(USERNAME_KEY);
    window.sessionStorage.setItem(USERNAME_KEY, username);
  }

  public getUsername(): string {
    return window.sessionStorage.getItem(USERNAME_KEY);
  }

  public saveAuthorities(authorities: string[]) {
    window.sessionStorage.removeItem(AUTHORITIES_KEY);
    window.sessionStorage.setItem(AUTHORITIES_KEY, JSON.stringify(authorities));
  }

  public getAuthorities(): string[] {
    return JSON.parse(window.sessionStorage.getItem(AUTHORITIES_KEY));
  }

  public isAdmin() {
    return this.getAuthorities() ? this.getAuthorities().indexOf('ADMIN') !== -1 : false;
  }

  public isUser() {
    return this.getAuthorities() ? this.getAuthorities().indexOf('USER') !== -1 : false;
  }
}
