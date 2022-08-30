import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BieresComponent } from './bieres.component';

describe('BieresComponent', () => {
  let component: BieresComponent;
  let fixture: ComponentFixture<BieresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BieresComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BieresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
