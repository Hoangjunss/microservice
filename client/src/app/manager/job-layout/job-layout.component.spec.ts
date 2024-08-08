import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobLayoutComponent } from './job-layout.component';

describe('JobLayoutComponent', () => {
  let component: JobLayoutComponent;
  let fixture: ComponentFixture<JobLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JobLayoutComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JobLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
