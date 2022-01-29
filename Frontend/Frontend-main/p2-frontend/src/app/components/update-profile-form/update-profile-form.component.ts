import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-update-profile-form',
  templateUrl: './update-profile-form.component.html',
  styleUrls: ['./update-profile-form.component.css']
})
export class UpdatePostFormComponent implements OnInit {
  // variables to be set from session storage
  userObj: any = {};
  @Output() sendOutputText: EventEmitter<string> = new EventEmitter();
  outputText: string = 'view';
  isEmailTaken: boolean = false;
  displayUrl: any;

  imageForm = this.fb.group({
    image: [null]
  })

  updateProfileForm = this.fb.group({
    userId: [null],
    username: [''],
    email: ['', {
      validators: [Validators.required, Validators.email]
    }],
    password: [null, Validators.minLength(8)],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    proPicUrl: [''],
    bday: [''],
    aboutMe: ['']
  })

  constructor(private userService: UserService, private fb: FormBuilder, private router: Router) { }

  ngOnInit(): void {
    this.userObj = JSON.parse(sessionStorage.getItem('userObj'));

    this.updateProfileForm.get('userId').setValue(this.userObj.userId);
    this.updateProfileForm.get('username').setValue(this.userObj.username);
    this.updateProfileForm.get('email').setValue(this.userObj.email);
    this.updateProfileForm.get('firstName').setValue(this.userObj.firstName);
    this.updateProfileForm.get('lastName').setValue(this.userObj.lastName);
    this.updateProfileForm.get('proPicUrl').setValue(this.userObj.proPicUrl);
    this.updateProfileForm.get('bday').setValue(this.userObj.bday);
    this.updateProfileForm.get('aboutMe').setValue(this.userObj.aboutMe);
  }

  onFileInput(event: any) {
    if (event.currentTarget.files.length > 0) {
      const file = event.currentTarget.files[0];

      let reader = new FileReader();
      reader.readAsDataURL(file);

      reader.onload = (_event) => {
        this.displayUrl = reader.result;
      }

      this.imageForm.get('image').setValue(file, {emitModelToViewChange: false});
    }
  }

  uploadImageAndUpdateProfile(event: any) {

    if (this.updateProfileForm.invalid) {
      return;
    }

    if (this.imageForm.get('image').value != null) {

      const formData = new FormData();
      formData.append('file', this.imageForm.get('image')!.value);

      this.userService.addProfileImage(formData)
        .subscribe(
          data => {

            this.updateProfileForm.patchValue({
              proPicUrl: data.data
            })

            this.updateProfile();
          }
        )
    } else {
      this.updateProfileForm.patchValue({
        proPicUrl: this.userObj.proPicUrl
      })

      this.updateProfile();
    }
  }

  updateProfile() {

    this.userService.updateProfile(this.updateProfileForm.value)
      .subscribe(
        user => {

          this.userObj = user.data;

          if (user.success) {
            sessionStorage.setItem('userObj', JSON.stringify(this.userObj));
            this.router.navigateByUrl('userFeed');
            this.sendOutputText.emit('update');
          }

        },
        error => {
          this.isEmailTaken = true;
        }
      )
  }

  resetEmailTakenMessage(){
    this.isEmailTaken = false;
  }

  get firstName() { return this.updateProfileForm.get('firstName') }
  get lastName() { return this.updateProfileForm.get('lastName') }
  get email() { return this.updateProfileForm.get('email') }
  get username() { return this.updateProfileForm.get('username') }
  get password() { return this.updateProfileForm.get('password') }
}
