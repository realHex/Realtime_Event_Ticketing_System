import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlsTopbarComponent } from './controls-topbar.component';

describe('ControlsTopbarComponent', () => {
  let component: ControlsTopbarComponent;
  let fixture: ComponentFixture<ControlsTopbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ControlsTopbarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ControlsTopbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
