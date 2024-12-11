import { TestBed } from '@angular/core/testing';

import { CountPollingService } from './count-polling.service';

describe('PollingService', () => {
  let service: CountPollingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CountPollingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
