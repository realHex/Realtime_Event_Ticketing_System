import { TestBed } from '@angular/core/testing';

import { MasterControlService } from './master-control.service';

describe('MasterControlService', () => {
  let service: MasterControlService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MasterControlService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
