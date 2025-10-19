import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportComment } from './report-comment';

describe('ReportComment', () => {
  let component: ReportComment;
  let fixture: ComponentFixture<ReportComment>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReportComment]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportComment);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
