import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeExchangeDialogComponent } from './home-exchange-dialog.component';

describe('HomeExchangeDialogComponent', () => {
  let component: HomeExchangeDialogComponent;
  let fixture: ComponentFixture<HomeExchangeDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomeExchangeDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeExchangeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
