import { TestBed } from '@angular/core/testing';

import { LogPollingService } from './log-polling.service';

describe('LogPollingService', () => {
  let service: LogPollingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LogPollingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
