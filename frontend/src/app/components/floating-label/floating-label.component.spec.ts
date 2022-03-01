import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FloatingLabelComponent } from './floating-label.component';

describe('FloatingLabelComponent', () => {
  let component: FloatingLabelComponent;
  let fixture: ComponentFixture<FloatingLabelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FloatingLabelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FloatingLabelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
