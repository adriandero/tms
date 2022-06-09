import { Component, OnInit } from '@angular/core';
import { faBars, faFilter, faSort } from '@fortawesome/free-solid-svg-icons';
import { MatDialog } from '@angular/material/dialog';
import { FilterOverlayComponent } from '../filter-overlay/filter-overlay.component';

@Component({
  selector: 'app-startpage',
  templateUrl: './startpage.component.html',
  styleUrls: ['./startpage.component.css'],
})
export class StartpageComponent implements OnInit {
  constructor(public filterDialog: MatDialog, public sortDialog: MatDialog) {}
  public showfilter: boolean = true;
  public showsort: boolean = true;

  public toogle() {
    this.showfilter = !this.showfilter;
    console.log(this.showfilter);
  }

  public toggleSort() {
    this.showsort = !this.showsort;
  }
  ngOnInit(): void {}

  faFilter = faFilter;
  faBars = faBars;
  faSort = faSort;
  sorts: string[] = [
    'alphapetical a-z',
    'alphapetical z-a',
    'By relevance',
    'Latest',
    'Oldest',
  ];

  openDialog() {
    const dialogRef = this.filterDialog.open(FilterOverlayComponent, {
      closeOnNavigation: true,
      maxWidth: '550px',
      maxHeight: '550px',
      width: '80vw',
      height: 'auto',

      // height: '600px',
      // width: '700px'
    });
  }
}
