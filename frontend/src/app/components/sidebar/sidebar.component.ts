import {ChangeDetectorRef, Component} from '@angular/core';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {finalize, Observable} from 'rxjs';
import {map, shareReplay} from 'rxjs/operators';
import {User} from "../../model/User";
import {Role} from "../../model/Roles";
import {
  faArchive,
  faCertificate,
  faCog,
  faInbox,
  faSearch,
  faStar,
  faSyncAlt,
  faTag,
  faTrash
} from "@fortawesome/free-solid-svg-icons"
import {AuthService} from "../../auth";
import {FilterOverlayComponent} from "../filter-overlay/filter-overlay.component";


@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );
    user: User = {
      id: 0, img: undefined, latestRefreshToken: "",
      firstname: "mAXimilian",
      lastname: "MuSTERmann",
      mail: "maximilian.mustermann@snt.at",
      roles: [Role.BASIC]
    }
    faSearch = faSearch
    faInbox = faInbox
    faStar = faStar
    faArchive = faArchive
    faTrash = faTrash
    faTag = faTag
    faCog=faCog
    faCertificate = faCertificate
    faSyncAlt = faSyncAlt

all_pages = [true, false, false, false] // all  -mytenders - new - rejected

  constructor(private breakpointObserver: BreakpointObserver, private cdr: ChangeDetectorRef, private authService: AuthService) {
 /*   this.filterOverlayComponent.getFilter().subscribe(val => {
      for (let user in val.users) {
        if (user === this.user.firstname || user === this.user.lastname) {
          this.all_pages[1] = true;
          this.all_pages[0] = false;
        }
      }
    }*/
   // )
  }


 refresh(){
      this.authService.refreshToken();
  }
}
