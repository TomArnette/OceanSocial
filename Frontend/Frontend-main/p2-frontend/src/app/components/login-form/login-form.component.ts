import { Component, Output, EventEmitter, Input } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { UserService } from 'src/app/services/user/user.service';
import { first } from 'rxjs/operators';
import { User } from 'src/app/models/User';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {

  signUpLabel: string = "Sign Up";
  logInLabel: string = "Log In"
  isValid: boolean|null = null;

  // variables to be set from session storage
  userObj: any = {};

  loginForm = this.fb.group({
    username: [null, Validators.required],
    password: [null, Validators.required]
  })

  constructor(private fb: FormBuilder, private router: Router, private userService: UserService) { }

  logIn(event: any){
    if (this.loginForm.invalid) {
      return;
    }

    this.userService.login(this.loginForm.value)
      .subscribe(
        data => {

          if (data.success) {
            let user = data.data;

            sessionStorage.setItem('userObj', JSON.stringify(user));

            sessionStorage.setItem('JWT', data.message);

            this.router.navigateByUrl('userFeed');
          } else {
            alert("Incorrect username or password");
          }
        },
        error => {

        }
      )
  }

  @Output() toggle: EventEmitter<any> = new EventEmitter();
  toggleForm(data: string): void {
    this.toggle.emit(data);
  }

  sendPasswordEmail(event: any) {}

  get username() { return this.loginForm.get('username') }
  get password() { return this.loginForm.get('password') }

}


