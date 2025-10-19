import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportForm } from './report-form';

describe('ReportForm', () => {
  let component: ReportForm;
  let fixture: ComponentFixture<ReportForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReportForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
