import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule} from '@angular/router';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {GravatarModule} from 'ngx-gravatar';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {UserNamePipe} from './pipes/user-name-pipe.pipe';
import {FloatingLabelComponent} from './components/floating-label/floating-label.component';
import {MatCardModule} from "@angular/material/card";
import {StartpageComponent} from './components/startpage/startpage.component';
import { LoginComponent } from './components/login/login.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatDividerModule} from '@angular/material/divider';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatListModule } from '@angular/material/list';
import {MatInputModule} from '@angular/material/input';
import { TenderComponent } from './components/tender/tender.component';
import {MatSelectModule} from '@angular/material/select';
import {MatMenuModule} from '@angular/material/menu';
import {MatChipsModule} from '@angular/material/chips';
import {MatBadgeModule} from '@angular/material/badge';
import {ReactiveFormsModule} from "@angular/forms";
import { TenderOverlayComponent } from './components/tender-overlay/tender-overlay.component';
import { OverlayModule } from "@angular/cdk/overlay";



@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SidebarComponent,
    TenderComponent,
    UserNamePipe,
    FloatingLabelComponent,
    StartpageComponent,
    LoginComponent,
    TenderOverlayComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    GravatarModule,
    FontAwesomeModule,
    MatCardModule,
    RouterModule,
    MatFormFieldModule,
    MatCheckboxModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    MatDividerModule,
    LayoutModule,
    MatListModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule,
    MatMenuModule,
    MatChipsModule,
    MatBadgeModule,
    ReactiveFormsModule,
    OverlayModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
