import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostList } from './post-list';

describe('PostList', () => {
  let component: PostList;
  let fixture: ComponentFixture<PostList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
