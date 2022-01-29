import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { UserService } from 'src/app/services/user/user.service';
import { DebugElement } from '@angular/core';
import { NotificationsComponent } from './notifications.component';

describe('NotificationsComponent', () => {
  let component: NotificationsComponent;
  let fixture: ComponentFixture<NotificationsComponent>;
  let debugElement: DebugElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule],
      declarations: [ NotificationsComponent ],
      providers: [UserService]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NotificationsComponent);
    component = fixture.componentInstance;
    debugElement = fixture.debugElement;
    fixture.detectChanges();
  });

  xit('should get all notifications based on user', fakeAsync(() => {
    let userId: number = 30
    let userService = fixture.debugElement.injector.get(UserService);

    spyOn(userService, "getUserNotifications").and.callFake(() => {return of(jasmine.arrayContaining([
        {
          feedId: 146,
          response: null,
          timestamp: 1633375296219,
          type: "like"
        }]))
      })

    component.getAllNotifications(userId);

    expect(component.notifications).toEqual(jasmine.arrayContaining([
      {
        feedId: 146,
        response: null,
        timestamp: 1633375296219,
        type: "like"
      }]))
  }));
});
