import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Tender} from "../../model/Tender";
import {stringify} from "querystring";
import {TenderService} from "../../services/tender.service";
import {BackendResponse} from "../../model/protocol/Response";
import { User } from 'src/app/auth';

export interface Assignment {
 // id : number,
  tender: Tender;
  user: User; //TODO user id or user
  hasUnseenChanges: boolean;
  instruction: string;

}

@Component({
  selector: 'create-assignment',
  templateUrl: 'create-assignment.component.html',
  styleUrls: ['./create-assignment.component.css'],
})


export class CreateAssignmentComponent {


  assignments: Assignment[] = []
  addOnBlur = true;

  constructor(
    private dialogRef: MatDialogRef<CreateAssignmentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private tenderService: TenderService) {
    this.assignments = data.assignments

  }

  userOptions: string[] = ['example@gmail.com'];
  task: string = "";
  mail: string = "";
  user: User;
  userunknown = false;

  create() {
    console.log("create");
    let assigned_user = this.tenderService.getUser(this.mail).subscribe({
        next: (sent: any) => {
          const response: BackendResponse<User> = sent;

          if ( response.statusCodeValue == 200){
            this.user = response.body
            console.log(this.user)
            let ass: Assignment = {
             // id: Math.floor((Math.random() * 100) + 1), //TODO ensure unique Assignment id
              tender: this.data.tender,
              instruction: this.task,
              hasUnseenChanges: true,
              user: this.user
            }
            this.postCreatedAssignment(ass);
            this.assignments.push(ass)
            this.task = "";
            this.mail = "";
            this.dialogRef.close(this.assignments);
          } else{
            this.handleError()
          }
        },
        error: (err: any) => {
          console.log(err)
        }
      })





  }

  addMail(mail: string) {
    this.mail = mail;
  }

  addTask(task: string) {
    this.task = task;
  }

  handleError(){
    this.userunknown = true;
  }
  postCreatedAssignment(ass : Assignment){
    console.log("postCreatedAssignment")
    return this.tenderService.postAssignments(ass).subscribe(
      {
        next: (sent :any) => {
          this.dialogRef.close(this.assignments);
        }
      }
    )

  }


}
