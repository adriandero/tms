import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {faSearch} from '@fortawesome/free-solid-svg-icons';

import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {MatChipInputEvent} from '@angular/material/chips';

import {BehaviorSubject, Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {Filter} from "../../model/Tender";
import {Router} from "@angular/router";
import {MatDatepickerInputEvent} from "@angular/material/datepicker";

export interface Chip {
  name: string;
}

@Component({
  selector: 'app-filter-overlay',
  templateUrl: './filter-overlay.component.html',
  styleUrls: ['./filter-overlay.component.css'],
})
export class FilterOverlayComponent implements OnInit, OnDestroy {
  fontStyleControl = new FormControl();
  shownfilter?: string;
  faSearch = faSearch;


  constructor(private router: Router) {
  }

  myControl = new FormControl();
  externOptions: string[] = [
    'Neuausschreibung',
    'Widerruf',
    'Berichtigung',
    'Fragenbeantwortung',
    'Bekanntmachung vergebender Auftrag',
  ];
  internOptions: string[] = [
    'interessant',
    'uninteressant',
    'abgeschlossen',
    'opportunity assessment',
    'aufgeschoben',
  ];

  filteredExternOptions!: Observable<string[]>;
  filteredInternOptions!: Observable<string[]>;

  platforms: Chip[] = [];
  companies: Chip[] = [];
  titles: Chip[] = [];
  intStatus: Chip[] = [];
  extStatus: Chip[] = [];
  files: Chip[] = [];
  uptDetails: Chip[] = [];
  users: Chip[] = [];
  startDate: Date = new Date();
  endDate: Date = new Date();
  // tasks: string[] = [];
  // filteredTaskOptions!: Observable<string[]>;
  ngOnInit() {
    this.filteredExternOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map((value) => this._filter(value, this.externOptions))
    );
    this.filteredInternOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map((value) => this._filter(value, this.internOptions))
    );
    let filterResult = localStorage.getItem("filter");
    if (filterResult != null) {
      console.log("apply preFilter")
      let pre_filter: Filter = JSON.parse(filterResult);
      pre_filter.platforms.map(e => this.platforms.push({name: e}));
      pre_filter.companies.map(e => this.companies.push({name: e}));
      pre_filter.titles.map(e => this.titles.push({name: e}));
      pre_filter.intStatus.map(e => this.intStatus.push({name: e}));
      pre_filter.extStatus.map(e => this.extStatus.push({name: e}));
      pre_filter.files.map(e => this.files.push({name: e}));
      pre_filter.uptDetails.map(e => this.uptDetails.push({name: e}));
      pre_filter.users.map(e => this.users.push({name: e}));
    }


    // this.filteredTaskOptions = this.myControl.valueChanges.pipe(
    //   startWith(''),
    //   map(value => this._filter(value, this.tasks)),
    // );
  }

  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  filter: Filter = {
    "platforms": this.platforms.map(e => e.name),
    "companies": this.companies.map(e => e.name),
    "titles": this.titles.map(e => e.name),
    "intStatus": this.intStatus.map(e => e.name),
    "extStatus": this.extStatus.map(e => e.name),
    "files": this.files.map(e => e.name),
    "uptDetails": this.uptDetails.map(e => e.name),
    "users": this.users.map(e => e.name),
    "startDate": this.startDate,
    "endDate": this.endDate,
    "sortBy": localStorage.getItem("sort") ?? "DEFAULT",
  }
  filter$ = new BehaviorSubject<Filter>(this.filter);


  private _filter(value: string, options: string[]): string[] {
    const filterValue = value.toLowerCase();
    return options.filter((option) =>
      option.toLowerCase().includes(filterValue)
    );
  }

  add(event: MatChipInputEvent, chipSet: Chip[]): void {
    const input = event.input;
    const value = event.value;

    // Add our platform
    if ((value || '').trim()) {
      chipSet.push({name: value.trim()});
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  remove(chip: Chip, chipSet: Chip[]): void {
    const index = chipSet.indexOf(chip);

    if (index >= 0) {
      chipSet.splice(index, 1);
    }
  }

  submit() {
    this.filter = {
      "platforms": this.platforms.map(e => e.name),
      "companies": this.companies.map(e => e.name),
      "titles": this.titles.map(e => e.name),
      "intStatus": this.intStatus.map(e => e.name),
      "extStatus": this.extStatus.map(e => e.name),
      "files": this.files.map(e => e.name),
      "uptDetails": this.uptDetails.map(e => e.name),
      "users": this.users.map(e => e.name),
      "startDate": this.startDate,
      "endDate": this.endDate,
      "sortBy": localStorage.getItem("sort") ?? "DEFAULT",
    }
    console.log("Filters submitted" + JSON.stringify(this.filter))
    localStorage.setItem("filter", JSON.stringify(this.filter))
    this.router.navigateByUrl('/home');
  }

  resetDates() {
    this.endDate = new Date();
    this.startDate = new Date();
  }

  startChange(event: MatDatepickerInputEvent<any>) {
    this.startDate = event.value;
    console.log("startChange", event.value)
    console.log("startChange", this.startDate)
  }

  endChange(event: MatDatepickerInputEvent<any>) {
    this.endDate = event.value;
  }

  getFilter(): Observable<Filter> {
    return this.filter$;
  }

  ngOnDestroy(): void {
    this.submit()
  }
}
