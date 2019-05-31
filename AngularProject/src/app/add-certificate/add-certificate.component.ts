import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {DataService} from '../providers/data.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Certificate} from '../certificate';
import {Tag} from '../tag';

@Component({
  selector: 'app-add-certificate',
  templateUrl: './add-certificate.component.html',
  styleUrls: ['./add-certificate.component.css']
})
export class AddCertificateComponent implements OnInit {
  certificate: Certificate;
  tags: Array<Tag>;
  form: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private data: DataService,
              private http: HttpClient) {
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      name: ['', Validators.compose([Validators.required])],
      tags: [[]],
      price: ['', Validators.compose([Validators.required])],
      description: ['', Validators.compose([Validators.required])]
    });
    this.data.certificate.subscribe(c => {
      this.certificate = c;
      if (this.router.url === '/edit') {
        this.form = this.formBuilder.group({
          name: [this.certificate.name, Validators.compose([Validators.required])],
          tags: [this.certificate.tags],
          price: [this.certificate.price, Validators.compose([Validators.required])],
          description: [this.certificate.description, Validators.compose([Validators.required])]
        });
      }
    });
  }

  onAdd() {
    const object = this.modifyCertificate();
    delete object.id;
    object.duration = 12;
    const path = 'http://localhost:8080/certificates';
    const headers = new HttpHeaders()
      .append('Authorization', 'Bearer ' + JSON.parse(window.sessionStorage.getItem('token')).access_token)
      .append('Content-type', 'application/json');
    this.http.post(path, object, {headers}).subscribe(data => {
      this.router.navigate(['certificates']);
    });
  }

  onEdit() {
    const object = this.modifyCertificate();
    const path = 'http://localhost:8080/certificates/' + object.id;
    const headers = new HttpHeaders()
      .set('Authorization', 'Bearer ' + JSON.parse(window.sessionStorage.getItem('token')).access_token)
      .set('Content-type', 'application/json');
    console.log(object);
    this.http.put(path, object, {headers}).subscribe(data => {
      this.router.navigate(['certificates']);
    });
  }

  onDelete() {
    const path = 'http://localhost:8080/certificates/' + this.certificate.id;
    const headers = new HttpHeaders()
      .set('Authorization', 'Bearer ' + JSON.parse(window.sessionStorage.getItem('token')).access_token)
      .set('Content-type', 'application/json');
    this.http.delete(path, {headers}).subscribe(data => {
      this.router.navigate(['certificates']);
    });
  }

  modifyCertificate() {
    const modify = new Certificate();
    Object.assign(modify, this.certificate);
    modify.name = this.form.value.name;
    modify.description = this.form.value.description;
    modify.tags = this.form.value.tags.map(tag => {
      if (isNaN(tag.id)) { delete tag.id; }
      return tag;
    });
    return modify;
  }

}
