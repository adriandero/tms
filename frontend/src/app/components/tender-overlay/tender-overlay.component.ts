import {Component, Inject} from '@angular/core';
import {faArrowLeft, faArrowRight, faFileAlt, faTimes} from "@fortawesome/free-solid-svg-icons";
import {Router} from '@angular/router';
import {MAT_DIALOG_DATA, MatDialog} from "@angular/material/dialog"; // import router from angular router
import {AssignedIntStatus, Tender} from 'src/app/model/Tender';
import {TenderService} from "../../services/tender.service";
import {User} from "../../model/User";

interface assignment {
  tender : Tender
  user : User
}
// import CloseIcon from '@material-ui/icons/Close';
@Component({
  selector: 'app-float-tender-view',
  templateUrl: './tender-overlay.component.html',
  styleUrls: ['./tender-overlay.css']
})


export class TenderOverlayComponent{
  faArrowRight = faArrowRight
  faTimes = faTimes
  faFileAlt=faFileAlt
  faArrowLeft=faArrowLeft




  // CloseIcon = CloseIcon
  constructor(@Inject(MAT_DIALOG_DATA) public data: Tender, public dialog: MatDialog,
              private route:Router
  ) {

  }



  ngOnInit(): void {

  }


  closeDialog(){
    this.dialog.closeAll()
  }
  // go(){
  // this.dialogRef.close();
  //this.route.navigate(['/history']);
  //}


}
