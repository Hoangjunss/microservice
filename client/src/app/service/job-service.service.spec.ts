import { TestBed } from '@angular/core/testing';

import { JobServiceService } from './job-service.service';

describe('JobServiceService', () => {
  let service: JobServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JobServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
