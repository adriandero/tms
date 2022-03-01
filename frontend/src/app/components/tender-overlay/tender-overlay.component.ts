import { Component, OnInit } from '@angular/core';
import {faArrowRight} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-float-tender-view',
  templateUrl: './tender-overlay.component.html',
  styleUrls: ['./tender-overlay.css']
})
export class TenderOverlayComponent implements OnInit {

  faArrowRight = faArrowRight
  constructor() { }

  ngOnInit(): void {
  }

}
