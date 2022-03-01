import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-floating-label',
  templateUrl: './floating-label.component.html',
  styleUrls: ['./floating-label.component.css']
})
export class FloatingLabelComponent implements OnInit {

  @Input() placeholder : string = ""
  ngOnInit(): void {
  }

}
