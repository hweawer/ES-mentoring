import {Component, OnInit} from '@angular/core';
import {CertificatesService} from './certificates.service';
import {TokenStorageService} from '../auth/token-storage.service';
import {NavigationEnd, Router} from '@angular/router';
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
  private certificates: Array<Certificate>;
  private roles: Array<string>;
  private first: number;
  private last: number;
  private next: number;
  private prev: number;

  constructor(private service: CertificatesService,
              private tokenStorage: TokenStorageService,
              private router: Router,
              private dataService: DataService) {
  }

  ngOnInit() {
    this.getCertificates();
    this.router.events.subscribe(
      (event) => {
        if (event instanceof NavigationEnd) {
        }
      });
  }

  getCertificates() {
    this.service.getCertificates(this.page, this.limit).subscribe(
      data => {
        this.certificates = (data as { collection: Array<Certificate>, links: Array<string> }).collection;
      },
      (error) => {
        console.log(error.error.message);
      }
    );
  }

  onEdit(cert: Certificate) {
    this.dataService.changeCertificate(cert);
    this.router.navigate(['edit']);
  }

  onDelete(cert: Certificate) {
    this.dataService.changeCertificate(cert);
    this.router.navigate(['edit']);
  }

  routeCertificate(cert: Certificate) {
    this.router.navigate(['/certificates', cert.id]);
  }
}
