import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NavigationEnd, Router} from '@angular/router';
import {ReservedService} from '../reserved.service';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-add-certificate',
  templateUrl: './add-certificate.component.html',
  styleUrls: ['./add-certificate.component.css']
})
export class AddCertificateComponent implements OnInit {
  tags: Array<string>;
  form: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private reservation: ReservedService,
              private http: HttpClient) { }

  ngOnInit() {
    let cert = null;
    if (this.reservation.certificate) {
      cert = JSON.parse(this.reservation.certificate);
      this.reservation.certificate = undefined;
    }

    if (cert) {
      this.form = this.formBuilder.group({
        name: [cert.name, Validators.compose([Validators.required])],
        tags: [cert.tags.map(tag => tag.name)],
        price: [cert.price, Validators.compose([Validators.required])],
        description: [cert.description, Validators.compose([Validators.required])]
      });
    } else {
      this.form = this.formBuilder.group({
        name: ['', Validators.compose([Validators.required])],
        tags: [''],
        price: ['', Validators.compose([Validators.required])],
        description: ['', Validators.compose([Validators.required])]
      });
    }
  }

  onAdd() {
    console.log('onadd');
    const path = 'http://localhost:8080/certificates';
    const object = this.formValueToObject();
    const headers = {
      Authorization: 'Bearer ' + JSON.parse(window.sessionStorage.getItem('token')).access_token,
      'Content-type': 'application/json'
    };
    console.log('headers : ' + headers.Authorization);
    this.http.post(path, object, {headers}).subscribe(data => {
      this.router.navigate(['certificates']);
    });
  }

  onEdit() {
    const object = this.formValueToObject();
  }

  onDelete() {

  }

  formValueToObject() {
    const object: { duration: number, price: number; name: string; description: string; tags: {name: string}[] } = {
      duration: 12,
      name: '',
      description: '',
      price: 0,
      tags: []
    };
    console.log(this.form.value);
    object.name = this.form.value.name;
    object.description = this.form.value.description;
    this.form.value.tags.forEach(tag => {
      const tagObj: {name: string} = {name: tag.value};
      object.tags.push(tagObj);
    });

    return object;
  }

}
