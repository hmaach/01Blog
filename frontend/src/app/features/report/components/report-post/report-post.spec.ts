import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportPost } from './report-post';

describe('ReportPost', () => {
  let component: ReportPost;
  let fixture: ComponentFixture<ReportPost>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReportPost]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportPost);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
