import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {HttpParams} from '@angular/common/http';
import {ApiService} from '../auth/api.service';

@Component({
  selector: 'app-root',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  siteName = 'CertificatesProject';
  login = 'Login';

  loginForm: FormGroup;
  invalidLogin = false;
  constructor(private formBuilder: FormBuilder, private router: Router, private apiService: ApiService) { }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }
    const body = new HttpParams()
      .set('username', this.loginForm.controls.username.value)
      .set('password', this.loginForm.controls.password.value)
      .set('grant_type', 'password');

    this.apiService.login(body.toString()).subscribe(data => {
      window.sessionStorage.setItem('token', JSON.stringify(data));
      this.router.navigate(['certificates']);
    }, error => {
      alert(error.error.error_description);
    });
  }

  ngOnInit() {
    window.sessionStorage.removeItem('token');
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.compose([Validators.required])],
      password: ['', Validators.required]
    });
  }
}
