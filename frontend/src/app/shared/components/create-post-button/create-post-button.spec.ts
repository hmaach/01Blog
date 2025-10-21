import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatePostButton } from './create-post-button';

describe('CreatePostButton', () => {
  let component: CreatePostButton;
  let fixture: ComponentFixture<CreatePostButton>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreatePostButton]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreatePostButton);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
