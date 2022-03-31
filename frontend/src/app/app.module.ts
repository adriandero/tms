import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule} from '@angular/router';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {GravatarModule} from 'ngx-gravatar';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {UserNamePipe} from './pipes/user-name-pipe.pipe';
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
import { LayoutModule } from '@angular/cdk/layout';
import { MatListModule } from '@angular/material/list';
import {MatInputModule} from '@angular/material/input';
import { TenderComponent } from './components/tender/tender.component';
import {MatSelectModule} from '@angular/material/select';
import {MatMenuModule} from '@angular/material/menu';
import {MatBadgeModule} from '@angular/material/badge';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { HistoryComponent } from './components/history/history.component';
import {MatStepperModule} from '@angular/material/stepper';
import {MatRippleModule} from '@angular/material/core';
import { TenderOverlayComponent } from './components/tender-overlay/tender-overlay.component';
import { OverlayModule } from "@angular/cdk/overlay";
import { TendersComponent } from './components/tenders/tenders.component';
import {HttpClientModule} from "@angular/common/http"
import {MatDialogModule} from "@angular/material/dialog";
import {FilterOverlayComponent} from "./components/filter-overlay/filter-overlay.component";
import {MatTabsModule} from "@angular/material/tabs";
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatChipsModule} from '@angular/material/chips';
import {MatDatepickerModule} from '@angular/material/datepicker'; 
import {MatNativeDateModule} from '@angular/material/core';
import {MatAutocompleteModule} from '@angular/material/autocomplete'; 

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    TenderComponent,
    UserNamePipe,
    StartpageComponent,
    LoginComponent,
    HistoryComponent,
    TenderOverlayComponent,
   TendersComponent,
    FilterOverlayComponent,
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
    MatAutocompleteModule,
    MatInputModule,
    MatNativeDateModule,
    MatSelectModule,
    MatMenuModule,
    MatChipsModule,
    MatTooltipModule,
    MatBadgeModule,
    FormsModule,
    MatStepperModule,
    ReactiveFormsModule,
    OverlayModule,
    HttpClientModule,
    MatDialogModule,
    MatRippleModule,
    MatTabsModule,
    MatDatepickerModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
