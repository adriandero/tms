import { Component, Inject, OnInit } from '@angular/core';
import {faArrowRight, faFileAlt,faArrowLeft, faTimes} from "@fortawesome/free-solid-svg-icons";
import {ComponentPortal} from "@angular/cdk/portal";
import {Overlay} from "@angular/cdk/overlay";
import {Router} from '@angular/router';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from "@angular/material/dialog"; // import router from angular router
import { Tender } from 'src/app/model/Tender';


// import CloseIcon from '@material-ui/icons/Close';
@Component({
  selector: 'app-float-tender-view',
  templateUrl: './tender-overlay.component.html',
  styleUrls: ['./tender-overlay.css']
})



export class TenderOverlayComponent{
  faArrowRight = faArrowRight
  faFileAlt=faFileAlt
  faArrowLeft=faArrowLeft

  faTimes=faTimes
  // CloseIcon = CloseIcon
  constructor(@Inject(MAT_DIALOG_DATA) public data: Tender, public dialog: MatDialog, public dialogRef: MatDialogRef<TenderOverlayComponent>,
  private route:Router
    ) { 

  }

 // go(){
   // this.dialogRef.close();
    //this.route.navigate(['/history']);
  //}


}
