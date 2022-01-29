import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';

import { ForgotEmailComponent } from './forgot-email.component';

describe('ForgotEmailComponent', () => {
  let component: ForgotEmailComponent;
  let fixture: ComponentFixture<ForgotEmailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [ ForgotEmailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ForgotEmailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should send an email when the button is clicked', ()=>{

    spyOn(component, 'sendPasswordEmail');

    const button = fixture.nativeElement.querySelector('app-buttons');
    button.dispatchEvent(new Event('onClick'));
 
    expect(component.sendPasswordEmail).toHaveBeenCalled();

  })
  
  it('should emit to parent when hitting back to login link',()=>{

    spyOn(component.sendToParent, 'emit');
    const link = fixture.nativeElement.querySelector('a');
    link.dispatchEvent(new Event('click'));
 
    expect(component.sendToParent.emit).toHaveBeenCalled();

  })

});
