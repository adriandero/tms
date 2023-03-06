import {ChangeDetectorRef, Component, Input} from '@angular/core';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {BehaviorSubject, finalize, Observable} from 'rxjs';
import {map, shareReplay} from 'rxjs/operators';
import {Group} from "../../model/Tender";
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
import {AuthService, User} from "../../auth";
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
    startDate: null, //TODO default value
    endDate: null,
    sortBy: "DEFAULT",
  }

  @Input()
  filter: Filter = localStorage.getItem("filter") != null ? JSON.parse(localStorage.getItem("filter")!) : this._defaultFilter;

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
    this.filter = localStorage.getItem("filter") != null ? JSON.parse(localStorage.getItem("filter")!) : this._defaultFilter
  }

  ngOnChange(){
  }

  activateUnchecked() {
    this.filter = this._defaultFilter; // We can use default filter directly because site is reloaded.
    this.filter.intStatus = ["Unchecked"];

    this.refreshPage()
  }
  
  uncheckedActivated() {
    return this.filter.intStatus.length === 1 && this.filter.intStatus[0] === "Unchecked";
  }

  activateMine() {
    this.filter = this._defaultFilter; // We can use default filter directly because site is reloaded.
    this.filter.users = [this.user.mail]//new Array(sessionStorage.getItem("user") ?? ""),
    //this.all_pages[1] = true

    this.refreshPage()
  }

  mineActivated() {
    return this.filter.users.length === 1 && this.filter.users[0] === this.user.mail;
  }

  activateAll() {
    this.filter = this._defaultFilter

    this.refreshPage()
  }

  allActivated() {
    return JSON.stringify(this.filter) === JSON.stringify(this._defaultFilter);
  }

  activateRejected(){
    this.filter = this._defaultFilter; // We can use default filter directly because site is reloaded.
    this.filter.intStatus = ["Irrelevant"]

    this.refreshPage()
  }

  rejectedActivated() {
    return this.filter.intStatus.length === 1 && this.filter.intStatus[0] === "Irrelevant";
  }

  refreshPage() {
    localStorage.setItem("filter", JSON.stringify(this.filter))
    this.router.navigateByUrl('/RefreshComponent', {skipLocationChange: true}).then(() => {
      this.router.navigate(['home']);
    });
  }
}

