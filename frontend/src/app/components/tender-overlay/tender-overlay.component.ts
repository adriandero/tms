import {Component, Inject, Injectable} from '@angular/core';
import {
  faArrowLeft,
  faArrowRight,
  faFileAlt,
  faTimes,
} from '@fortawesome/free-solid-svg-icons';
import {Router} from '@angular/router';
import {MAT_DIALOG_DATA, MatDialog} from '@angular/material/dialog'; // import router from angular router
import {AssignedIntStatus, Tender} from 'src/app/model/Tender';
import {TenderService} from '../../services/tender.service';
import {User} from '../../model/User';
import {Hidden} from '@material-ui/core';

import {CreateAssignmentComponent} from '../create-assignment/create-assignment.component';

// import CloseIcon from '@material-ui/icons/Close';
@Component({
  selector: 'app-float-tender-view',
  templateUrl: './tender-overlay.component.html',
  styleUrls: ['./tender-overlay.css'],
})
export class TenderOverlayComponent {
  faArrowRight = faArrowRight;
  faTimes = faTimes;
  faFileAlt = faFileAlt;
  faArrowLeft = faArrowLeft;
  assignments: any = [];
  hasUpdates: boolean = true;

  // CloseIcon = CloseIcon
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Tender,
    public dialog: MatDialog,
    private route: Router
  ) {
  }

  ngOnInit(): void {
    if (this.data.updates.length == 0) {
      this.hasUpdates = false;

      document.getElementById('history-btn')!.style.display = 'none';

      /*
      let historyBtn = document.getElementById('history-btn');

      historyBtn!.style.color = '#555555ad';
      let nodes = document
        .getElementById('history-btn')!
        .getElementsByTagName('fa-icon') as HTMLCollectionOf<HTMLElement>;
      for (var i = 0; i < nodes.length; i++) {
        nodes[i].style.color = '#555555ad';
      }
      */
    } else {
      document.getElementById('history-btn-disabled')!.style.display = 'none';
    }
  }

  openDialog() {
    const dialogRef = this.dialog.open(CreateAssignmentComponent, {
      closeOnNavigation: true,
      maxWidth: '400px',
      maxHeight: '300px',
      data: {
        dataKey: {assignments : this.assignments, tender : this.data}
      }
    });
  }

  changeRoute(): void {
    if (this.hasUpdates) {
      this.dialog.closeAll();

      this.route.navigate(['/history', this.data.id], {
        state: this.data,
      });
    }
  }

  closeDialog() {
    this.dialog.closeAll();
  }

  // go(){
  // this.dialogRef.close();
  //this.route.navigate(['/history']);
  //}
}
