import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MediaGrid } from './media-grid';

describe('MediaGrid', () => {
  let component: MediaGrid;
  let fixture: ComponentFixture<MediaGrid>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MediaGrid]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MediaGrid);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
