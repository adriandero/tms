import { Component, Input, OnInit } from '@angular/core';
import {
  faAngleDown,
  faArrowLeft,
  faFileAlt,
  faTag,
  faUser,
} from '@fortawesome/free-solid-svg-icons';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
  FormArray,
} from '@angular/forms';
import { Tender } from '../../model/Tender';
import { Location } from '@angular/common';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css'],
})
export class HistoryComponent {
  faAngleDown = faAngleDown;
  faUser = faUser;
  faTag = faTag;
  faFileAlt = faFileAlt;
  faArrowLeft = faArrowLeft;

  isReadMore = true;

  showText() {
    this.isReadMore = !this.isReadMore;
  }

  /**
   * @title Stepper overview
   */
  @Component({
    selector: 'stepper-overview-example',
    templateUrl: 'stepper-overview-example.html',
    styleUrls: ['stepper-overview-example.css'],
  })
  isLinear = false;
  formGroup!: FormGroup;
  form!: FormArray;

  constructor(private _formBuilder: FormBuilder, private location: Location) {}

  dates: String[] = [history.state];

  get refArray() {
    return this.formGroup.get('form') as FormArray;
  }

  ngOnInit() {
    this.formGroup = this._formBuilder.group({
      form: this._formBuilder.array(this.dates),
    });
    console.log(this.location.getState());
  }

  init() {
    return this._formBuilder.group({
      //cont: new FormControl('', [Validators.required]),
    });
  }

  addItem() {
    this.form = this.formGroup.get('form') as FormArray;
    this.form.push(this.init());
  }
}
