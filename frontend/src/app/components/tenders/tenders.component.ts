import {Component, OnInit} from '@angular/core';
import {Filter, Tender} from '../../model/Tender';
import {TenderService} from 'src/app/services/tender.service';
import {BackendResponse} from 'src/app/model/protocol/Response';
import {Router} from "@angular/router";

@Component({
  selector: 'app-tenders',
  templateUrl: './tenders.component.html',
  styleUrls: ['./tenders.component.css'],
})
export class TendersComponent implements OnInit {
  tenders: Tender[] = [];

  constructor(private tenderService : TenderService, private router: Router){
    this.router.routeReuseStrategy.shouldReuseRoute = () => {
      return false;
    };
  }

  ngOnInit(): void {
    let _filter = localStorage.getItem("filter") ?? '{}';
    this.tenderService.getTenders(JSON.parse(_filter)).subscribe({
      next: (sent: any) => {
        const response: BackendResponse<Tender[]> = sent;
        let ten: Tender = response.body[0];
        this.tenders = response.body;
      },
    });
  }

}
