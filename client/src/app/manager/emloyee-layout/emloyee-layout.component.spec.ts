import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmloyeeLayoutComponent } from './emloyee-layout.component';

describe('EmloyeeLayoutComponent', () => {
  let component: EmloyeeLayoutComponent;
  let fixture: ComponentFixture<EmloyeeLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmloyeeLayoutComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmloyeeLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
