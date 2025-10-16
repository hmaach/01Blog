import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileReadme } from './profile-readme';

describe('ProfileReadme', () => {
  let component: ProfileReadme;
  let fixture: ComponentFixture<ProfileReadme>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileReadme]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileReadme);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
