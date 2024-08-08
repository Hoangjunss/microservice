import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavabarComponent } from './navabar.component';

describe('NavabarComponent', () => {
  let component: NavabarComponent;
  let fixture: ComponentFixture<NavabarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavabarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavabarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
