import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportUser } from './report-user';

describe('ReportUser', () => {
  let component: ReportUser;
  let fixture: ComponentFixture<ReportUser>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReportUser]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportUser);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
