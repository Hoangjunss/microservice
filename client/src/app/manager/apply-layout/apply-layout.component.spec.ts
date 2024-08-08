import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplyLayoutComponent } from './apply-layout.component';

describe('ApplyLayoutComponent', () => {
  let component: ApplyLayoutComponent;
  let fixture: ComponentFixture<ApplyLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ApplyLayoutComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApplyLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
