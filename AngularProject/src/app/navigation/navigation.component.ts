import {Component, Inject, LOCALE_ID, OnInit} from '@angular/core';
import * as jwt_decode from 'jwt-decode';
import {NavigationEnd, Router} from '@angular/router';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {
  private localizationSwitch = 'EN/RU';
  private username: string;
  roles: Array<string>;

  constructor(@Inject(LOCALE_ID) protected localeId: string, private router: Router) { }

  ngOnInit() {
    this.router.events.subscribe(
      (event) => {
        if (event instanceof NavigationEnd) {
          this.initToken();
        }
      });
    this.initToken();
  }

  isAdmin() {
    return this.roles.indexOf('ADMIN') !== -1;
  }

  isUser() {
    return this.roles.indexOf('USER') !== -1;
  }

  initToken() {
    const token = window.sessionStorage.getItem('token');
    if (token) {
      const decodedToken = jwt_decode(window.sessionStorage.getItem('token'));
      this.username = decodedToken.user_name;
      this.roles = decodedToken.authorities;
    } else {
      this.roles = ['GUEST'];
    }
  }

  logout() {
    window.sessionStorage.removeItem('token');
    window.location.reload();
  }
}
