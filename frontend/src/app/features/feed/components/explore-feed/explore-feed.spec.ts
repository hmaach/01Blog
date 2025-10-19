import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExploreFeed } from './explore-feed';

describe('ExploreFeed', () => {
  let component: ExploreFeed;
  let fixture: ComponentFixture<ExploreFeed>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExploreFeed]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExploreFeed);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
