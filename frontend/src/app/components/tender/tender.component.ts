import { Component, Input, OnInit } from '@angular/core';
import {
  faAngleDown,
  faArrowRight,
  faTag,
  faUser,
} from '@fortawesome/free-solid-svg-icons';
import { Tender, Status } from '../../model/Tender';
import { MatDialog } from '@angular/material/dialog';
import { TenderOverlayComponent } from '../tender-overlay/tender-overlay.component';
import { TenderService } from 'src/app/services/tender.service';
import { BackendResponse } from 'src/app/model/protocol/Response';
import { Router } from '@angular/router';
import { STATUS_CODES } from 'http';

@Component({
  selector: 'app-tender',
  templateUrl: './tender.component.html',
  styleUrls: ['./tender.component.css'],
})
export class TenderComponent implements OnInit {
  @Input() tender!: Tender;

  matBadge: number = 0;

  isOpen = false;
  faArrowRight = faArrowRight;
  faAngleDown = faAngleDown;
  faUser = faUser;
  faTag = faTag;

  hidden = true;

  constructor(private tenderService : TenderService, public dialog: MatDialog, private router: Router) {}
  ngOnInit(): void {
    if (this.tender.latestUpdate != null) this.matBadge += 1;
    if (this.tender.updates != null)
      this.matBadge += this.tender.updates.length;

    if (this.matBadge > 0) this.hidden = false;

    const predictClass = document.getElementsByClassName(
      'prediction'
    ) as HTMLCollectionOf<HTMLElement>;

    for (let i = 0; i < predictClass.length; i++) {
      if (this.tender.predictionAccuracy < 0) {
        predictClass[i].style.display = 'none';
      }
    }

    this.fetchTransitions();
  }

  checkBoxVisible() {
    return this.tender.latestIntStatus.label === "Unchecked"; // Label is primary key for internal status
  }

  clicked() {
    const target = this.tender.intStatusTransitions!.find(status => status.label.toUpperCase() === this.tender.predictedIntStatus)!;

    this.tenderService.updateInternalStatus(this.tender, target).subscribe(item => {
      if(item.statusCodeValue === 200) {
        this.tender.latestIntStatus = target;
      }
    });
  }

  fetchTransitions() {
    this.tenderService.getInternalStatusTransitions(this.tender.latestIntStatus).subscribe({
      next: (sent: any) => {
        const response: BackendResponse<Status[]> = sent;
        
        this.tender.intStatusTransitions = response.body;
      },
    });
  }

  changeIntStatus(status: Status) {
    this.tenderService.updateInternalStatus(this.tender, status).subscribe(item => {
      if(item.statusCodeValue === 200) {
        this.tender.latestIntStatus = status;

        this.fetchTransitions();
      }
    });
  }

  openDialog() {
    const dialogRef = this.dialog.open(TenderOverlayComponent, {
      closeOnNavigation: true,
      maxWidth: '700px',
      maxHeight: '700px',
      height: '80vh',
      data: this.tender,
    });
  }
}
