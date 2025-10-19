import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileBlock } from './profile-block';

describe('ProfileBlock', () => {
  let component: ProfileBlock;
  let fixture: ComponentFixture<ProfileBlock>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileBlock]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileBlock);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
