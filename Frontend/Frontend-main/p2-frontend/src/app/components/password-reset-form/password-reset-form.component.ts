import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-password-reset-form',
  templateUrl: './password-reset-form.component.html',
  styleUrls: ['./password-reset-form.component.css']
})
export class PasswordResetFormComponent implements OnInit {

  passwordResetForm = this.fb.group({
    password: [null, [Validators.required, Validators.minLength(8)]],
    verify: [null, [Validators.required, Validators.minLength(8)]]
  })

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
  }

  // required for form validation
  get password() { return this.passwordResetForm.get('password') }
  get verify() { return this.passwordResetForm.get('verify') }
}
