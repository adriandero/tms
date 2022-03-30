import { Component, OnInit } from '@angular/core';
import {FormControl} from "@angular/forms";
import {faSearch} from "@fortawesome/free-solid-svg-icons"

import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {MatChipInputEvent} from '@angular/material/chips';

export interface Chip {
  name: string;
}

@Component({
  selector: 'app-filter-overlay',
  templateUrl: './filter-overlay.component.html',
  styleUrls: ['./filter-overlay.component.css']
})
export class FilterOverlayComponent implements OnInit {

  fontStyleControl = new FormControl();
  shownfilter?: string;
  faSearch = faSearch

  constructor() { }

  ngOnInit(): void {
  }
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  chips: Chip[] = [];

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our Chip
    if ((value || '').trim()) {
      this.chips.push({name: value.trim()});
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  remove(chip: Chip): void {
    const index = this.chips.indexOf(chip);

    if (index >= 0) {
      this.chips.splice(index, 1);
    }
  }

}
