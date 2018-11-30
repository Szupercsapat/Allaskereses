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
import { AuthGuardService } from './auth/auth-guard.service';
import { HomeComponent } from './home/home.component';
import { ProfilesComponent } from './profiles/profiles.component';
import { CVService } from './profiles/cv.service';
import { ProfileUpdateService } from './profiles/update.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    HeaderComponent,
    HomeComponent,
    ProfilesComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [
    LoginService, LogoutService, RegistrationService,
    CookieService, SessionService, AuthGuardService,
    CVService, ProfileUpdateService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
