import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Certificate} from '../certificate';
import {HttpClient} from '@angular/common/http';
import {CertificatesService} from '../certificates/certificates.service';

@Component({
  selector: 'app-certificate',
  templateUrl: './certificate.component.html',
  styleUrls: ['./certificate.component.css']
})
export class CertificateComponent implements OnInit {
  certificate: Certificate;
  constructor(private route: ActivatedRoute,
              private router: Router,
              private http: HttpClient,
              private certificateService: CertificatesService) { }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.certificateService.getCertificateById(id).subscribe(data => {
      this.certificate = data as Certificate;
    },
    error => {
      console.log(error);
    });
  }

}
