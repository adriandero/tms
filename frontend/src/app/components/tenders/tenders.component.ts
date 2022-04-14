import { Component, OnInit } from '@angular/core';
import {Tender} from "../../model/Tender";
import { TenderService } from 'src/app/services/tender.service';
import { tap } from 'rxjs';

@Component({
  selector: 'app-tenders',
  templateUrl: './tenders.component.html',
  styleUrls: ['./tenders.component.css']
})
export class TendersComponent implements OnInit {

  tenders: Tender[] = [];

  constructor(private tenderService: TenderService) { }

  ngOnInit(): void {

    this.tenderService.getTenders().subscribe({
      next: (data: any) => {
        for (let i = 0; i < data.length; i++) {
          let tender: Tender = data[i].body;
          this.tenders.push(tender);
        }
      }
});
        
    
    
  }
 

}
