import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { SignupFormComponent } from './signup-form.component';

describe('SignupFormComponent', () => {
  let component: SignupFormComponent;
  let fixture: ComponentFixture<SignupFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, RouterTestingModule, HttpClientTestingModule],
      providers: [
        { provide: Router, useValue: null }
      ],
      declarations: [ SignupFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SignupFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /* it('should create', () => {
    expect(component).toBeTruthy();
  }); */


  it('should emit to parent when login here link is clicked', ()=>{

    spyOn(component.toggle, 'emit');
    const link = fixture.nativeElement.querySelector('a');
    link.dispatchEvent(new Event('click'));
 
    expect(component.toggle.emit).toHaveBeenCalled();

  })

  it('should call signUp when the button is clicked', ()=>{

    spyOn(component, 'signUp');

    const button = fixture.nativeElement.querySelector('app-buttons');
    button.dispatchEvent(new Event('onClick'));

    expect(component.signUp).toHaveBeenCalled();

  })
});
