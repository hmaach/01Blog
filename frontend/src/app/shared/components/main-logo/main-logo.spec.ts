import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainLogo } from './main-logo';

describe('MainLogo', () => {
  let component: MainLogo;
  let fixture: ComponentFixture<MainLogo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MainLogo]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MainLogo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
