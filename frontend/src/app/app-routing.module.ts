import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {StartpageComponent} from "./components/startpage/startpage.component";
import {LoginComponent} from "./components/login/login.component";
import {HistoryComponent} from "./components/history/history.component";

import {TenderOverlayComponent} from "./components/tender-overlay/tender-overlay.component";
import {AuthGuard} from "./auth/guards/auth.guard";

const routes: Routes = [
  { path: 'home', component: StartpageComponent , canActivate: [AuthGuard]},
 { path: 'login', component: LoginComponent },
  { path: 'history', component: HistoryComponent },
  { path: 'card', component: TenderOverlayComponent, canActivate: [AuthGuard]},
  { path: '',   redirectTo: '/home', pathMatch: 'full' },
  {
    path: '**',
    redirectTo: '/home'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
