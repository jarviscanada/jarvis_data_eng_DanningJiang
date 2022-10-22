import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewTweetPageComponent } from './new-tweet-page.component';

describe('NewTweetPageComponent', () => {
  let component: NewTweetPageComponent;
  let fixture: ComponentFixture<NewTweetPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewTweetPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewTweetPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
