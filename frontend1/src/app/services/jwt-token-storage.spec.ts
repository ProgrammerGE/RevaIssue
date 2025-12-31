import { TestBed } from '@angular/core/testing';

import { JwtTokenStorage } from './jwt-token-storage';

describe('JwtTokenStorage', () => {
  let service: JwtTokenStorage;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JwtTokenStorage);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
