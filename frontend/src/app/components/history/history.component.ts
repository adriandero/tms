import {Component} from '@angular/core';
import {faAngleDown, faArrowLeft, faFileAlt, faTag, faUser} from "@fortawesome/free-solid-svg-icons"

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent {
    faAngleDown=faAngleDown;
    faUser=faUser;
    faTag=faTag;
    faFileAlt = faFileAlt;
    faArrowLeft = faArrowLeft;

    isReadMore = true

  showText() {
     this.isReadMore = !this.isReadMore
  }

}
