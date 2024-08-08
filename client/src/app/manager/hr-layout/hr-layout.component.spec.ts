import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HrLayoutComponent } from './hr-layout.component';

describe('HrLayoutComponent', () => {
  let component: HrLayoutComponent;
  let fixture: ComponentFixture<HrLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HrLayoutComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HrLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
