import {ChangeDetectorRef, Component, Input} from '@angular/core';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {BehaviorSubject, finalize, Observable} from 'rxjs';
import {map, shareReplay} from 'rxjs/operators';
import {Group, User} from "../../model/Tender";
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
import {Filter} from "../../model/Tender";
import {stringify} from "querystring";
import {Router} from "@angular/router";


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
  user: User = JSON.parse(sessionStorage.getItem("user") ?? '{}'); // these braces should be unreachable cause guard kicks if not logged in.
  faSearch = faSearch
  faInbox = faInbox
  faStar = faStar
  faArchive = faArchive
  faTrash = faTrash
  faTag = faTag
  faCog = faCog
  faCertificate = faCertificate
  faSyncAlt = faSyncAlt


   page_alltenders : Observable<boolean>;
  page_mytender : Observable<boolean>
  page_rejectedtenders : Observable<boolean>
  page_latesttenders : Observable<boolean>

  _defaultFilter: Filter = {
    platforms: [],
    companies: [],
    titles: [],
    intStatus: [],
    extStatus: [],
    files: [],
    uptDetails: [],
    users: [],
    startDate: new Date(Date.UTC(2018, 11, 1, 0, 0, 0)), //TODO default value
    endDate: new Date(Date.now()),
    sortBy: "DEFAULT",
  }

  @Input()
  filter: Filter = {
    platforms: [],
    companies: [],
    titles: [],
    intStatus: [],
    extStatus: [],
    files: [],
    uptDetails: [],
    users: [],
    startDate: new Date(Date.UTC(2018, 11, 1, 0, 0, 0)), //TODO default value
    endDate: new Date(Date.now()),
    sortBy: "DEFAULT",
  }

  all_pages  = [false, false, false, false] // all  -mytenders - new - rejected

  //ngOnChanges() {
  //  console.log("ngonChanges()")
  //  console.log(this.all_pages)
    //console.log(JSON.stringify(sessionStorage.getItem("user")));
    //console.log(JSON.parse(JSON.stringify(sessionStorage.getItem("user"))));

    //let _curuser: User = JSON.parse(JSON.stringify(sessionStorage.getItem("user"))); //TODO parse object
    //console.log("_curuser" + _curuser.username);
   /* if (this.filter.users.indexOf(this.user.username) !== -1) {
      console.log("myTenders()")
      this.all_pages[1] = true
    }
    if (this.filter.startDate > new Date(Date.now() - 12096e5)) {
      console.log("latestTenders()")
      this.all_pages[2] = true
    }
    if (this.filter.intStatus.indexOf("uninteressant")) {
      console.log("rejectedTenders()")
      this.all_pages[3] = true
    }*/
   /* if (this.filter = this._defaultFilter) {
      this.all_pages = [true, false, false, false] // all  -mytenders - new - rejected
    } else {

      this.all_pages[0] = false

    }*/

 // }

  constructor(private breakpointObserver: BreakpointObserver, private cdr: ChangeDetectorRef, private authService: AuthService, private router: Router) {
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

  ngOnInit() {

  }

  ngOnChange(){
  }
  refresh() {
    this.authService.refreshToken();
  }

  activateLatest() {
    this.filter.startDate = new Date(Date.now() - 12096e5);
    this.filter.endDate = new Date(Date.now());
    this.filter.sortBy = "LATEST"
    this.all_pages = [false, false, true, false]

    this.refreshPage()
  }

  activateMine() {
    console.log("activateMine")
    this.filter.users = [this.user.username]//new Array(sessionStorage.getItem("user") ?? ""),
    //this.all_pages[1] = true
    this.all_pages = [false, true, false, false]

    this.refreshPage()
  }

  activateAll() {
    console.log("activateAll")
    this.filter = this._defaultFilter

    this.refreshPage()
  }

  activateRejected(){
    this.filter.intStatus = ["uninteressant"]
    this.all_pages = [false, false, false, true]

    this.refreshPage()
  }

  refreshPage() {
    console.log("refresh page")
    localStorage.setItem("filter", JSON.stringify(this.filter))
    this.router.navigateByUrl('/RefreshComponent', {skipLocationChange: true}).then(() => {
      this.router.navigate(['home']);
    });
  }
}

