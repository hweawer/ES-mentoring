import { Component, OnInit } from '@angular/core';
import {CertificatesService} from './certificates.service';
import {TokenStorageService} from '../auth/token-storage.service';
import {NavigationEnd, Router} from '@angular/router';
import * as jwt_decode from 'jwt-decode';
import {Certificate} from '../certificate';
import {DataService} from '../providers/data.service';

@Component({
  selector: 'app-certificates',
  templateUrl: './certificates.component.html',
  styleUrls: ['./certificates.component.css']
})
export class CertificatesComponent implements OnInit {
  private page = 1;
  private limit = 10;
  private certificates: Array<any>;
  private roles: Array<string>;
  private first: number;
  private last: number;
  private next: number;
  private prev: number;

  constructor(private service: CertificatesService,
              private storage: TokenStorageService,
              private router: Router,
              private dataService: DataService) { }

  ngOnInit() {
    this.getCertificates();
    this.router.events.subscribe(
      (event) => {
        if (event instanceof NavigationEnd) {
          this.initToken();
        }
      });
    this.initToken();
  }

  getCertificates() {
    this.service.getCertificates(this.page, this.limit).subscribe(
      data => {
        this.certificates = data['collection'];
      },
      // tslint:disable-next-line:no-shadowed-variable
      (error) => {
        console.log(error.error.message);
      }
    );
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
      this.roles = decodedToken.authorities;
    } else {
      this.roles = ['GUEST'];
    }
  }

  onEdit(cert: Certificate) {
    this.dataService.changeCertificate(cert);
    this.router.navigate(['edit']);
  }

  onDelete(cert: Certificate) {
    this.dataService.changeCertificate(cert);
    this.router.navigate(['edit']);
  }
}
