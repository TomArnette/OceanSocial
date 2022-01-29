import { HttpClientTestingModule } from '@angular/common/http/testing';
import { DebugElement } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { UserService } from 'src/app/services/user/user.service';

import { IndexComponent } from './index.component';

describe('IndexComponent', () => {
  let component: IndexComponent;
  let fixture: ComponentFixture<IndexComponent>;
  let debugElement: DebugElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ RouterTestingModule, HttpClientTestingModule ],
      declarations: [ IndexComponent ],
      providers: [ FormBuilder, UserService ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IndexComponent);
    component = fixture.componentInstance;
    debugElement = fixture.debugElement;
    fixture.detectChanges();
  });

  it("should navigate to userFeed page if sessionStorage.getItem('JWT') is null", () => {
    const storage = {};
    fixture.detectChanges();
    spyOn(sessionStorage, 'getItem').and.callFake((key) => {
      return storage[key];
    })
    expect(storage[0]).toBeFalsy();
  }) 

  //To be tested after JWT has been implemented
  xit("should navigate to userFeed page if sessionStorage.getItem('JWT') is not null", () => {
    let router = {
      navigate: jasmine.createSpy('navigate')
    }

    sessionStorage.setItem('userId', '1');
    const storage = {};
    fixture.detectChanges();

    spyOn(sessionStorage, 'getItem').and.callFake((key) => {
      return storage[key];
    })
    
    expect(router.navigate).toHaveBeenCalledWith(['/userFeed/0']);
  })

  it("should toggle the current variable when toggle method is called to 'forgot'", () => {
    let data: string = "forgot";
    expect(component.current).toContain('login');
    component.toggle(data);
    expect(component.current).toContain('forgot');
  }) 

  it("should call backToLogin method and set current variable to 'login'", () => {
    component.current = "forgot";
    component.backToLogin();
    expect(component.current).toContain('login');
  });
});
