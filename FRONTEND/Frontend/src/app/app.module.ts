import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HttpModule } from '@angular/http';
import { LoginComponent } from './auth/login/login.component';
import { RegistrationComponent } from './auth/registration/registration.component';
import { LoginService } from './auth/login/login.service';
import { RegistrationService } from './auth/registration/registration.service';
import { AppRoutingModule } from './app-routing.module';
import { HeaderComponent } from './header/header.component';
import { CookieService } from '../../node_modules/ngx-cookie-service';
import { LogoutService } from './auth/logout.service';
import { SessionService } from './auth/session.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [LoginService, LogoutService, RegistrationService, CookieService, SessionService],
  bootstrap: [AppComponent]
})
export class AppModule { }
