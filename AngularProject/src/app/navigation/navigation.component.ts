import {Component, OnInit} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
import {TokenStorageService} from '../auth/token-storage.service';
import {LoginService} from '../login/login.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {
  private username: string;

  constructor(private router: Router,
              private tokenStorage: TokenStorageService,
              private loginService: LoginService) {
  }

  ngOnInit() {
    this.router.events.subscribe(
      (event) => {
        if (event instanceof NavigationEnd) {
          if (this.tokenStorage.getToken()) {
            this.username = this.tokenStorage.getUsername();
          }
        }
      });
  }

  logout() {
    this.loginService.logout();
    this.router.navigate(['certificates']);
  }
}
