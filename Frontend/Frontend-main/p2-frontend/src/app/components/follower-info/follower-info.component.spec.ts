import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { User } from 'src/app/models/User';

import { FollowerInfoComponent } from './follower-info.component';

describe('FollowerInfoComponent', () => {
  let component: FollowerInfoComponent;
  let fixture: ComponentFixture<FollowerInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FollowerInfoComponent ],
      imports: [HttpClientTestingModule, RouterTestingModule]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FollowerInfoComponent);
    component = fixture.componentInstance;
    window.sessionStorage.setItem('userObj', JSON.stringify(new User()))
    fixture.detectChanges();

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
