import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {CertificatesComponent} from './certificates/certificates.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'certificates', component: CertificatesComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class RoutingModule { }
// export const routingComponents = [LoginComponent, CertificatesComponent];
