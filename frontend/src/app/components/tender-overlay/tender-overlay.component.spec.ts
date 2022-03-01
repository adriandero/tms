import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TenderOverlayComponent } from './tender-overlay.component';

describe('FloatTenderViewComponent', () => {
  let component: TenderOverlayComponent;
  let fixture: ComponentFixture<TenderOverlayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TenderOverlayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TenderOverlayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
