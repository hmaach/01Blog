import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SwitchMode } from './switch-mode';

describe('SwitchMode', () => {
  let component: SwitchMode;
  let fixture: ComponentFixture<SwitchMode>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SwitchMode]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SwitchMode);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
