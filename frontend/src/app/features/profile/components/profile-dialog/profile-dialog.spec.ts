import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileDialog } from './profile-dialog';

describe('ProfileDialog', () => {
  let component: ProfileDialog;
  let fixture: ComponentFixture<ProfileDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileDialog]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
