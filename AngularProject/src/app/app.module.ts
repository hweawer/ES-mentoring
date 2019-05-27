import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { NavigationComponent } from './navigation/navigation.component';
import { FooterComponent } from './footer/footer.component';
import { RoutingModule } from './routing.module';
import { LoginComponent } from './login/login.component';
import { CertificatesComponent } from './certificates/certificates.component';
import { RootComponent } from './root/root.component';
import {httpInterceptorProviders} from './auth/auth-interceptor';
import {HttpClientModule} from '@angular/common/http';
import {ApiService} from './auth/api.service';
import {CertificatesService} from './certificates/certificates.service';

@NgModule({
  declarations: [
    RootComponent,
    LoginComponent,
    NavigationComponent,
    FooterComponent,
    CertificatesComponent
  ],
  imports: [
    BrowserModule.withServerTransition({ appId: 'serverApp' }),
    FormsModule,
    RoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [ httpInterceptorProviders, ApiService, CertificatesService ],
  bootstrap: [RootComponent]
})
export class AppModule { }
