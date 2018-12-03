import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegistrationComponent } from './auth/registration/registration.component';
import { AuthGuardService } from './auth/auth-guard.service';
import { HomeComponent } from './home/home.component';
import { ProfilesComponent } from './profiles/profiles.component';
import { SearchProfilesComponent } from './search/search-profiles/search-profiles.component';
import { SearchWorkplacesComponent } from './search/search-workplaces/search-workplaces.component';

const appRoutes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuardService] },
  { path: 'login', component: LoginComponent },
  { path: 'profile/:id', component: ProfilesComponent, canActivate: [AuthGuardService] },
  { path: 'search-profiles', component: SearchProfilesComponent, canActivate: [AuthGuardService] },
  { path: 'search-workplaces', component: SearchWorkplacesComponent, canActivate: [AuthGuardService] },
  { path: 'register', component: RegistrationComponent },
  { path: 'logout', redirectTo: '/login', canActivate: [AuthGuardService]}
  /*{
    path: 'servers',
    //canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    component: ServersComponent,
    children: [
    { path: ':id', component: ServerComponent, resolve: {server: ServerResolver} },
    { path: ':id/edit', component: EditServerComponent, canDeactivate: [CanDeactivateGuard] }
  ] },
  //{ path: 'not-found', component: PageNotFoundComponent },
  { path: 'not-found', component: ErrorPageComponent, data: {message: 'Page not found!'} },
  { path: '**', redirectTo: '/not-found'}*/
];

@NgModule({
  imports: [
    // RouterModule.forRoot(appRoutes, {useHash: true})
    RouterModule.forRoot(appRoutes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
