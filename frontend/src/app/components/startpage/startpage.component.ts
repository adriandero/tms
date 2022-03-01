import { Component, OnInit } from '@angular/core';
import {faFilter, faSort, faBars} from "@fortawesome/free-solid-svg-icons"


@Component({
  selector: 'app-startpage',
  templateUrl: './startpage.component.html',
  styleUrls: ['./startpage.component.css']
})
export class StartpageComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  faFilter=faFilter;
  faBars=faBars;
  faSort=faSort;
}
