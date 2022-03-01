import { Component } from '@angular/core';
import {faAngleDown, faUser,faTag} from "@fortawesome/free-solid-svg-icons"


@Component({
  selector: 'app-tender',
  templateUrl: './tender.component.html',
  styleUrls: ['./tender.component.css']
})
export class TenderComponent {
    faAngleDown=faAngleDown;
    faUser=faUser;
    faTag=faTag;
}
