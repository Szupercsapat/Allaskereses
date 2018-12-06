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
import { ProfileUpdateService } from './profiles/update.service';
// import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SearchProfilesComponent } from './search/search-profiles/search-profiles.component';
import { SearchWorkplacesComponent } from './search/search-workplaces/search-workplaces.component';
import { ActualService } from './shared/actual.service';
import { GetProfileService } from './profiles/getProfile.service';
import { ImageService } from './profiles/image.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    HeaderComponent,
    HomeComponent,
    ProfilesComponent,
    SearchProfilesComponent,
    SearchWorkplacesComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    AppRoutingModule/*,
    NgbModule*/
  ],
  providers: [
    LoginService, LogoutService, RegistrationService,
    CookieService, SessionService, AuthGuardService,
    ProfileUpdateService, ActualService, GetProfileService,
    ImageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
