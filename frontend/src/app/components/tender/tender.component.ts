import { Component,Input,OnInit } from '@angular/core';
import {faAngleDown, faUser,faTag} from "@fortawesome/free-solid-svg-icons"
import {Tender} from "../../model/Tender";
import {faArrowRight} from "@fortawesome/free-solid-svg-icons";
import {MatDialog} from "@angular/material/dialog";
import {TenderOverlayComponent} from "../tender-overlay/tender-overlay.component";
import {Router} from '@angular/router'; // import router from angular router
import { FlexibleConnectedPositionStrategy } from '@angular/cdk/overlay';
import { AutofillMonitor } from '@angular/cdk/text-field';

@Component({
  selector: 'app-tender',
  templateUrl: './tender.component.html',
  styleUrls: ['./tender.component.css']
})


export class TenderComponent implements OnInit{

   @Input() tender!: Tender;
  constructor(public dialog: MatDialog) {}
  ngOnInit(): void {
    
  }
  isOpen = false;
  faArrowRight = faArrowRight
  faAngleDown=faAngleDown
  faUser=faUser
  faTag=faTag


  openDialog() {
      
    const dialogRef = this.dialog.open(TenderOverlayComponent, {
      closeOnNavigation: true,
      maxWidth: '700px',
      minWidth: '600px',
      maxHeight: '700px',
      minHeight: '400px',
      height: '80vh',
      data: this.tender
    });



  }

}

