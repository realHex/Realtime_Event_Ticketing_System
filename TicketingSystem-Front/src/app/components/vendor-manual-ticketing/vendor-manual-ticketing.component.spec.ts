import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorManualTicketingComponent } from './vendor-manual-ticketing.component';

describe('VendorManualTicketingComponent', () => {
  let component: VendorManualTicketingComponent;
  let fixture: ComponentFixture<VendorManualTicketingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VendorManualTicketingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendorManualTicketingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
