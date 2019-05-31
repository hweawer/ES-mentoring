import {Injectable} from '@angular/core';
import {ReplaySubject} from 'rxjs';
import {Certificate} from '../certificate';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  certificate = new ReplaySubject<Certificate>();

  constructor() {
  }

  changeCertificate(certificate: Certificate) {
    this.certificate.next(certificate);
  }
}
