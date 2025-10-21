import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MediaPreview } from './media-preview';

describe('MediaPreview', () => {
  let component: MediaPreview;
  let fixture: ComponentFixture<MediaPreview>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MediaPreview]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MediaPreview);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
