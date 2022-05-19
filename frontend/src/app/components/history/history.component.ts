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
import { Tender, TenderUpdate } from '../../model/Tender';
import { Location } from '@angular/common';

import { TenderService } from 'src/app/services/tender.service';
import { BackendResponse } from 'src/app/model/protocol/Response';
import { ConditionalExpr } from '@angular/compiler';
import { Router } from '@angular/router';

/**
 * Todo
 * overlay component gibt id weiter an history
 * dort fetch den spezifischen tender
 * schau welche tenders mit ihm in verbindung liegen
 * packs in ein array
 * bau den stepper
 *
 * tender hat id - (ist in json angegeben) - wird weitergegeben und kannst dan einzelt fetchen das tender
 *
 *
 * do it in reverse - request data from parent? - or do it on load or something - cuz if u just press on it then it wont get fetched by just calling the page
 */

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

  tender?: Tender = this.router.getCurrentNavigation()!.extras.state as Tender;

  updates: TenderUpdate[] = [];
  dates: string[] = [];
  hasUpdates: boolean = true;

  constructor(
    private _formBuilder: FormBuilder,
    private location: Location,
    private tenderService: TenderService,
    private router: Router
  ) {
    console.log(this.tender);

    //parse or smth

    /*
    let numOfUpd: number = this.tender?.updates.length!;
    for (let i = 0; i <= numOfUpd; i++) {
      this.updates.push(this.tender.updates);
      console.log('update added');
    }
    */
  }

  ngOnInit(): void {
    this.updates = this.tender?.updates!;
    this.tender?.latestUpdate != null
      ? this.updates.unshift(this.tender?.latestUpdate!)
      : (this.hasUpdates = false);

    /*
    this.tenderService.getTenders().subscribe({
      next: (sent: any) => {
        const response: BackendResponse<Tender[]> = sent;

        this.tenders = response.body;
        console.log(this.tenders);
      },
    });
    */
    for (let i = 0; i < this.updates.length; i++) {
      this.updates[i].validFrom = new Date(
        this.updates[i].validFrom
      ).toLocaleDateString();
    }
    console.log(this.dates);

    this.formGroup = this._formBuilder.group({
      form: this._formBuilder.array(this.updates),
    });
    for (let i = 0; i < this.updates.length; i++) {
      if (this.updates[i].attachedFiles.length < 1) {
        console.log(document.getElementById('anhaenge'));
      }
    }
  }

  get refArray() {
    return this.formGroup.get('form') as FormArray;
  }

  ngAfterContentInit() {}

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
