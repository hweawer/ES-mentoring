import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NavigationEnd, Router} from '@angular/router';
import {HttpParams} from '@angular/common/http';
import {LoginService} from './login.service';
import {TOKEN_KEY, TokenStorageService} from '../auth/token-storage.service';
import * as jwt_decode from 'jwt-decode';

const USERNAME_PARAM = 'username';
const PASSWORD_PARAM = 'password';
const GRANT_TYPE_PARAM = 'grant_type';

@Component({
  selector: 'app-root',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  invalidLogin = false;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private loginService: LoginService,
              private tokenStorage: TokenStorageService) {
  }

  ngOnInit() {
    this.loginService.logout();
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.compose([Validators.required])],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }

    const body = new HttpParams()
      .set(USERNAME_PARAM, this.loginForm.controls.username.value)
      .set(PASSWORD_PARAM, this.loginForm.controls.password.value)
      .set(GRANT_TYPE_PARAM, 'password');

    this.loginService.login(body.toString()).subscribe(data => {
      const tokenString = (data as { access_token: string }).access_token;
      this.initStorageWithToken(tokenString);

      this.router.navigate(['certificates']);
    }, error => {
      alert(error.error.error_description);
    });
  }

  initStorageWithToken(data: string) {
    this.tokenStorage.saveToken(data);
    const object = jwt_decode(data);
    this.tokenStorage.saveUsername(object.user_name);
    this.tokenStorage.saveAuthorities(object.authorities);
  }
}
