import { TestBed } from '@angular/core/testing';

import { BiereService } from './biere.service';

describe('BiereService', () => {
  let service: BiereService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BiereService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
