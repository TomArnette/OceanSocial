import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-signup-form',
  templateUrl: './signup-form.component.html',
  styleUrls: ['./signup-form.component.css']
})
export class SignupFormComponent {
  @Output() toggle: EventEmitter<any> = new EventEmitter();
  signUpLabel: string = "Sign Up";
  signInLabel: string = "Sign In";

  signupForm = this.fb.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    email: ['', {
      validators: [Validators.required, Validators.email]
    }],
    username: ['', Validators.required],
    password: ['', [Validators.required, Validators.minLength(8)]]
  })

  constructor(private fb: FormBuilder, private router: Router, private userService: UserService) { }

  signUp(event: any) {
    if (this.signupForm.invalid) {
      return;
    }

    sessionStorage.setItem('userObj', JSON.stringify(this.signupForm.value));
    this.router.navigateByUrl('createProfile');
  }

  toggleForm(): void {
    this.toggle.emit("login");
  }

  get firstName() { return this.signupForm.get('firstName') }
  get lastName() { return this.signupForm.get('lastName') }
  get email() { return this.signupForm.get('email') }
  get username() { return this.signupForm.get('username') }
  get password() { return this.signupForm.get('password') }

}
