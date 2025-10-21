import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostForm } from './post-form';

describe('PostForm', () => {
  let component: PostForm;
  let fixture: ComponentFixture<PostForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
