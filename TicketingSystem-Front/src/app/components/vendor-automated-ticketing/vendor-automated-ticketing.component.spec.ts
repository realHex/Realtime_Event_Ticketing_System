import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorAutomatedTicketingComponent } from './vendor-automated-ticketing.component';

describe('VendorAutomatedTicketingComponent', () => {
  let component: VendorAutomatedTicketingComponent;
  let fixture: ComponentFixture<VendorAutomatedTicketingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VendorAutomatedTicketingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendorAutomatedTicketingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
