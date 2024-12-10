import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketCountsComponent } from './ticket-counts.component';

describe('TicketCountsComponent', () => {
  let component: TicketCountsComponent;
  let fixture: ComponentFixture<TicketCountsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketCountsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketCountsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
