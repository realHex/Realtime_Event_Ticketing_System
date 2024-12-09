import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationControlsComponent } from './application-controls.component';

describe('ApplicationControlsComponent', () => {
  let component: ApplicationControlsComponent;
  let fixture: ComponentFixture<ApplicationControlsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ApplicationControlsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApplicationControlsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
