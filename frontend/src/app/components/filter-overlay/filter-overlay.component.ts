import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { faSearch } from '@fortawesome/free-solid-svg-icons';

import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatChipInputEvent } from '@angular/material/chips';

import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

export interface Chip {
  name: string;
}

@Component({
  selector: 'app-filter-overlay',
  templateUrl: './filter-overlay.component.html',
  styleUrls: ['./filter-overlay.component.css'],
})
export class FilterOverlayComponent implements OnInit {
  fontStyleControl = new FormControl();
  shownfilter?: string;
  faSearch = faSearch;

  constructor() {}

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
  platforms: Chip[] = [];
  companies: Chip[] = [];
  titles: Chip[] = [];
  intStatus: Chip[] = [];
  extStatus: Chip[] = [];
  files: Chip[] = [];
  uptDetails: Chip[] = [];
  users: Chip[] = [];

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
      chipSet.push({ name: value.trim() });
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
}
