import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {CertificatesComponent} from './certificates/certificates.component';
import {AddCertificateComponent} from './add-certificate/add-certificate.component';
import {CertificateComponent} from './certificate/certificate.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'certificates', component: CertificatesComponent },
  { path: 'certificates/:id', component: CertificateComponent },
  { path: 'add', component: AddCertificateComponent },
  { path: 'edit', component: AddCertificateComponent },
  { path: '', redirectTo: 'certificates', pathMatch: 'full' },
  { path: '**', redirectTo: 'certificates', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class RoutingModule { }
