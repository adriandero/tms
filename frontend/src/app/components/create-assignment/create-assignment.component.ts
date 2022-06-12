import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {Tender, User} from "../../model/Tender";

export interface Assignment{
  tender : Tender;
  id : number;
  user : string; //TODO user id or user
  hasUnseenChanges : boolean;
  instruction : string;

}
@Component({
  selector: 'create-assignment',
  templateUrl: 'create-assignment.component.html',
  styleUrls: ['./create-assignment.component.css'],
})


export class CreateAssignmentComponent {


  assignments : Assignment[]= []
  constructor( @Inject(MAT_DIALOG_DATA) public data: any,) {
    this.assignments = data.assignments
    console.log(this.assignments)
  }

  userOptions: string[] = ['exampl@gmail.com'];
  task :string = "";
  user : string;

  create(){
    let ass : Assignment= {tender: this.data.tender, id: 1, instruction: this.task, hasUnseenChanges: true, user: this.user }
    this.assignments.push(ass)
  }

}
