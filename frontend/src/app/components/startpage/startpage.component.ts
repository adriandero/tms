
import {Component, OnInit} from '@angular/core';
import {faBars, faFilter, faSort} from '@fortawesome/free-solid-svg-icons';
import {MatDialog} from '@angular/material/dialog';
import {FilterOverlayComponent} from '../filter-overlay/filter-overlay.component';
import {Router} from "@angular/router";
import {Filter} from "../../model/Tender";

@Component({
  selector: 'app-startpage',
  templateUrl: './startpage.component.html',
  styleUrls: ['./startpage.component.css'],
})
export class StartpageComponent implements OnInit {
  constructor(
    private router: Router,
    public filterDialog: MatDialog,
    public sortDialog: MatDialog
  ) {}

  public showfilter: boolean = true;
  public showsort: boolean = true;

  public toogle() {
    this.showfilter = !this.showfilter;
    console.log(this.showfilter);
  }

  public toggleSort() {
    this.showsort = !this.showsort;
  }

  ngOnInit(): void {
    //localStorage.setItem("sort", "DEFAULT")
  }

  faFilter = faFilter;
  faBars = faBars;
  faSort = faSort;
  sorts = {
    ALPHABETICAL_ASC: 'Alphabetical A-Z',
    ALPHABETICAL_DESC: 'Alphabetical Z-A',
    LATEST: 'Latest',
    OLDEST: 'Oldest',
  };

  filter : Filter = {
    platforms: [],
    companies: [],
    titles: [],
    intStatus: [],
    extStatus   : [],
    files   : [],
    uptDetails   : [],
    users   : [],
    startDate  : new Date(Date.UTC(2018, 11, 1, 0, 0, 0)), //TODO default value
    endDate   :  new Date( Date.now()),
    sortBy   : "DEFAULT"   ,
  }


  sortCategories() {
    return Object.keys(this.sorts);
  }

  categoryName(name: string) {
    return (this.sorts as any)[name];
  }

  isSort(sort: string) {
    return sort === localStorage.getItem('sort');
  }

  setSorting(sort: string) {
    if (localStorage.getItem('sort') === sort) {
      localStorage.setItem('sort', 'DEFAULT');
    } else {
      localStorage.setItem('sort', sort);
    }
    this.router.navigateByUrl('/RefreshComponent', { skipLocationChange: true }).then(() => {
      this.router.navigate(['home']);
    });
  }

  openDialog() {
    const dialogRef = this.filterDialog.open(FilterOverlayComponent, {
      closeOnNavigation: true,
      maxWidth: '550px',
      maxHeight: '550px',
      width: '80vw',
      height: 'auto',
      data: this.filter,
      autoFocus: true,

    });
    dialogRef.afterClosed().subscribe(
      data => { console.log("returned from overlay filter: " + data); this.filter = data}
    );

      // height: '600px',
      // width: '700px'
    }

}
