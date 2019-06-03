import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {DataService} from '../providers/data.service';
import {HttpClient} from '@angular/common/http';
import {Certificate} from '../certificate';
import {Tag} from '../tag';
import {CertificatesService} from '../certificates/certificates.service';
import {stringValidator} from '../custom-validators/validators';
import {ConfirmationDialogService} from '../conformation-dialog/conformation-dialog.service';

@Component({
  selector: 'app-add-certificate',
  templateUrl: './add-certificate.component.html',
  styleUrls: ['./add-certificate.component.css']
})
export class AddCertificateComponent implements OnInit {
  private certificate: Certificate;
  private form: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private data: DataService,
              private http: HttpClient,
              private certificateService: CertificatesService,
              private conformationService: ConfirmationDialogService) {
  }

  ngOnInit() {
    this.form = this.initForm();
    this.data.certificate.subscribe(c => {
      this.certificate = c;
      if (this.router.url === '/edit') {
        this.form = this.initForm(
          this.certificate.name,
          this.certificate.price,
          this.certificate.description,
          this.certificate.tags
        );
      }
    });
  }

  private onAdd() {
    const object = this.modifyCertificate();
    delete object.id;
    object.duration = 12;
    this.certificateService.createCertificate(object).subscribe(() => {
      this.router.navigate(['certificates']);
    });
  }

  private onEdit() {
    const object = this.modifyCertificate();
    this.certificateService.updateCertificate(object).subscribe(() => {
      this.router.navigate(['certificates']);
    });
  }

  private onDelete() {
    const object = this.modifyCertificate();
    this.certificateService.deleteCertificate(object).subscribe(() => {
      this.router.navigate(['certificates']);
    });
  }

  private modifyCertificate() {
    const modify = new Certificate();
    Object.assign(modify, this.certificate);
    modify.name = this.form.value.name;
    modify.description = this.form.value.description;
    modify.price = +this.form.value.price;
    modify.tags = this.form.value.tags.map(tag => {
      if (isNaN(tag.id)) {
        delete tag.id;
      }
      return tag;
    });
    return modify;
  }

  get name() {
    return this.form.get('name');
  }

  get description() {
    return this.form.get('description');
  }

  get price() {
    return this.form.get('price');
  }

  get tags() {
    return this.form.get('tags');
  }

  private initForm(defaultName?: string, defaultPrice?: number, defaultDescription?: string, defaultTags?: Array<Tag>) {
    const name = defaultName ? defaultName : null;
    const price = defaultPrice ? defaultPrice : null;
    const description = defaultDescription ? defaultDescription : null;
    const tags = defaultTags ? defaultTags : [];
    return this.formBuilder.group({
      name: [name,
        Validators.compose([
          Validators.minLength(3),
          Validators.maxLength(12),
          Validators.required,
          stringValidator(/[^a-zA-Z]+/)
        ])],
      tags: [tags],
      price: [price, Validators.compose([
        Validators.min(1),
        Validators.required
      ])],
      description: [description,
        Validators.compose([
          Validators.minLength(3),
          Validators.maxLength(1000),
          Validators.required
        ])]
    });
  }

  private tagValidator(): ValidatorFn {
    return stringValidator(/^([a-zA-Z0-9]){3,12}$/);
  }

  public openConfirmationDialog() {
    this.conformationService.confirm('Please confirm removing', 'Do you really want to delete the certificate?')
      .then((confirmed) => {
        if (confirmed) {
          this.onDelete();
        }
      })
      .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
  }
}
