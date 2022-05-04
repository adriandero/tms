import {Component} from '@angular/core';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {Observable} from 'rxjs';
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

  constructor(private breakpointObserver: BreakpointObserver) {}

}
