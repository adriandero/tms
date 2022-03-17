import { Component, OnInit } from '@angular/core';
import {Tender} from "../../model/Tender";
import { TenderService } from 'src/app/services/tender.service';

@Component({
  selector: 'app-tenders',
  templateUrl: './tenders.component.html',
  styleUrls: ['./tenders.component.css']
})
export class TendersComponent implements OnInit {

  tenders: Tender[] = [];

  constructor(private tenderService: TenderService) { }

  ngOnInit(): void {

    this.tenderService.getTenders().subscribe(
      (observedTenders) => this.tenders = observedTenders
    );
    this.tenders.forEach(p=> console.log(p));
    
  }

}
