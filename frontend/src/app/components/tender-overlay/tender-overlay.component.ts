import {Component, Inject} from '@angular/core';
import {faArrowLeft, faArrowRight, faFileAlt, faTimes} from "@fortawesome/free-solid-svg-icons";
import {Router} from '@angular/router';
import {MAT_DIALOG_DATA, MatDialog} from "@angular/material/dialog"; // import router from angular router
import {AssignedIntStatus, Tender} from 'src/app/model/Tender';
import {TenderService} from "../../services/tender.service";
import {User} from "../../model/User";
import {BackendResponse} from "../../model/protocol/Response";

interface Assignment{
  body :{
    id : number,
    tender : Tender[]
    instructions : string
    hasUnseenChanges: boolean
  }
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
  assignments :Assignment[] = []




  // CloseIcon = CloseIcon
  constructor(@Inject(MAT_DIALOG_DATA) public data: Tender, public dialog: MatDialog,
              private route:Router, private tenderService: TenderService
  ) {
console.log(data)
  }



  ngOnInit(): void {

    this.tenderService.getAssignments(this.data.id).subscribe({
      next: (sent: any) => {
        const response: BackendResponse<Assignment[]> = sent;
        this.assignments = response.body;
        console.log( response.body)
      }
    });
  }





  closeDialog(){
    this.dialog.closeAll()
  }
  // go(){
  // this.dialogRef.close();
  //this.route.navigate(['/history']);
  //}


}
