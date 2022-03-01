import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {User} from "../../model/User";
import {faArchive, faInbox, faSearch, faStar, faTrash} from "@fortawesome/free-solid-svg-icons";

import {Role} from "../../model/Roles";


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  encapsulation: ViewEncapsulation.None,
  providers: []

})


export class NavbarComponent implements OnInit {
  user: User = {
    firstname: "mAXimilian",
    lastname: "MuSTERmann",
    mail: "maximilian.mustermann@gmail.com",
    roles: [Role.BASIC]
  }
  faSearch = faSearch
  faInbox = faInbox
  faStar = faStar
  faArchive = faArchive
  faTrash = faTrash

  constructor() {
    //this.user = new User("Max Muster", "max.muster@gmx.at", Role.basic)
  }

  ngOnInit(): void {

  }

}
