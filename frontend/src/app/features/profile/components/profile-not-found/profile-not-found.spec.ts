import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileNotFound } from './profile-not-found';

describe('ProfileNotFound', () => {
  let component: ProfileNotFound;
  let fixture: ComponentFixture<ProfileNotFound>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileNotFound]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileNotFound);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
