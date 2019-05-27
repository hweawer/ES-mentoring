import { Component, OnInit } from '@angular/core';
import {CertificatesService} from './certificates.service';
import {TokenStorageService} from '../auth/token-storage.service';


@Component({
  selector: 'app-certificates',
  templateUrl: './certificates.component.html',
  styleUrls: ['./certificates.component.css']
})
export class CertificatesComponent implements OnInit {
  private page = 1;
  private limit = 10;
  private certificates: Array<any>;
  private first: number;
  private last: number;
  private next: number;
  private prev: number;

  constructor(private service: CertificatesService, private storage: TokenStorageService) { }

  ngOnInit() {
    this.getCertificates();
  }

  getCertificates() {
    this.service.getCertificates(this.page, this.limit).subscribe(
      data => {
        // console.log(data);
        this.certificates = data.collection;
      },
      // tslint:disable-next-line:no-shadowed-variable
      (error) => {
        console.log(error.error.message);
      }
    );
  }
}
