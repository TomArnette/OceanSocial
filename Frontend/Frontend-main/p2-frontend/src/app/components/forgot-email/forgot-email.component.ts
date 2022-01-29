import { Component, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-forgot-email',
  templateUrl: './forgot-email.component.html',
  styleUrls: ['./forgot-email.component.css']
})
export class ForgotEmailComponent {

  // This event is sent to the index page to toggle back to the login form
  @Output() sendToParent: EventEmitter<string> = new EventEmitter<string>();

  forgotForm = this.fb.group({
    username: [null, Validators.required]
  });

  constructor(private fb: FormBuilder, private userService: UserService) { }

  // this method is called to toggle back to login form
  backToLogin() {
    this.sendToParent.emit('login');
  }

  sendPasswordEmail(event: any) {
    if (this.forgotForm.invalid) {      
      return;
    }

    this.userService.forgotPassword(this.forgotForm.get('username').value)
      .subscribe(
        data => {
          if (data.success) {
            alert('A new password has been sent to your email');
            this.forgotForm.get('username').setValue('');
            this.sendToParent.emit('login');
          } else {
            alert("The username entered wasn't found");
          }
        },
        error => {
                    
        }
      );
    }

    // required for form validation
    get username() { return this.forgotForm.get('username') }
}
