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

@Component({
  selector: 'app-tender',
  templateUrl: './tender.component.html',
  styleUrls: ['./tender.component.css'],
})
export class TenderComponent implements OnInit {
  @Input() tender!: Tender;

  intStatus: Status[] = this.tender?.latestIntStatus.transitions;
  extStatus: Status[] = this.tender?.latestExtStatus.transitions;

  matBadge: number = 0;

  isOpen = false;
  faArrowRight = faArrowRight;
  faAngleDown = faAngleDown;
  faUser = faUser;
  faTag = faTag;

  hidden = true;

  constructor(public dialog: MatDialog) {}
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
      if (this.tender.predictedIntStatus != 'IRRELEVANT') {
        //todo - weird behaviour - look into it
        predictClass[i].style.backgroundColor = '#93e0a0';
      }
    }
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
