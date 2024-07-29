import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostProjectComponent } from './post-project.component';

describe('PostProjectComponent', () => {
  let component: PostProjectComponent;
  let fixture: ComponentFixture<PostProjectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostProjectComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
