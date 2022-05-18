import {Component, OnInit} from '@angular/core';
import {Tender} from "../../model/Tender";
import {TenderService} from 'src/app/services/tender.service';
import {BackendResponse} from 'src/app/model/protocol/Response';

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
      next: (sent: any) => {
        const response: BackendResponse<Tender[]> = sent;
        let ten :Tender= response.body[0]
        console.log(ten)
        this.tenders = response.body;
      }
    });
  }


}
