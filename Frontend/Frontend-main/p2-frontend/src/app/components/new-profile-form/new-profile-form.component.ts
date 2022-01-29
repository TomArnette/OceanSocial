import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { StringNullableChain } from 'lodash';
import { first } from 'rxjs/operators';
import { UserService } from 'src/app/services/user/user.service';
import { DateValidator } from 'src/app/validators/date-validator';

@Component({
  selector: 'app-new-profile-form',
  templateUrl: './new-profile-form.component.html',
  styleUrls: ['./new-profile-form.component.css']
})
export class NewProfileFormComponent implements OnInit {

  submitLabel: string = 'Submit';
  uploadLabel: string = 'Upload';
  current: string = 'image';
  imageUrl: string = 'https://s3.amazonaws.com/test.zimi.li/default.jpg';
  addedPic: boolean = false;
  displayUrl: any;
  warn: boolean = false;
  isLoading: boolean = false;

  // variables to be set from session storage
  userObj: any = {};

  imageForm = this.fb.group({
    file: [null]
  })

  newProfileForm = this.fb.group({
    username: [''],
    email: [''],
    firstName: [''],
    lastName: [''],
    password: [''],
    proPicUrl: [''],
    bday: ['', [Validators.required, DateValidator.usDate]],
    aboutMe: ['', Validators.required]
  })

  constructor(private fb: FormBuilder, private userService: UserService, private router: Router) { }

  ngOnInit() {
    this.userObj = JSON.parse(sessionStorage.getItem('userObj')!);

  }

  onFileInput(event: any) {
    if (event.currentTarget.files.length > 0) {
      const file = event.currentTarget.files[0];

      let reader = new FileReader();
      reader.readAsDataURL(file);

      reader.onload = (_event) => {
        this.displayUrl = reader.result;
      }

      this.imageForm.get('file').setValue(file, {emitModelToViewChange: false});

    }
  }

  switchForm(event: any) {
    this.current = "details";
  }

  checkImageSwitchForm(event: any) {
    if (this.imageForm.get('file').value != null) {
      this.current = "details";
    } else {
      this.warn = true;
    }
  }

  createProfile() {
    this.isLoading = true;

    this.newProfileForm.patchValue({
      username: this.userObj.username,
      email: this.userObj.email,
      firstName: this.userObj.firstName,
      lastName: this.userObj.lastName,
      password: this.userObj.password,
      proPicUrl: this.imageUrl,
    })

    this.userObj["bday"] = this.newProfileForm.get('bday')?.value;
    this.userObj["aboutMe"] = this.newProfileForm.get('aboutMe')?.value;

    sessionStorage.setItem('userObj', JSON.stringify(this.userObj));



    this.userService.register(this.newProfileForm.value)
    .subscribe(
      data => {
        this.isLoading = false;

        this.userObj["userId"] = data.data.userId;

        sessionStorage.setItem('userObj', JSON.stringify(this.userObj));
        sessionStorage.setItem('JWT', data.message);

        this.router.navigateByUrl('explore');
      },
      error => {

      }
    )
  }

  uploadImageAndCreateProfile(event: any) {
    if (this.imageForm.get('file').value != null) {
      const formData = new FormData();
      formData.append('file', this.imageForm.get('file').value);

      this.userService.addProfileImage(formData)
      .subscribe(
        data => {
          this.userObj["proPicUrl"] = data.data;

          this.imageUrl = data.data;
          this.createProfile();
        }
      )
    } else {
      this.userObj["proPicUrl"] = this.imageUrl;

      this.createProfile();
    }
  }

  get bday() { return this.newProfileForm.get('bday') }
  get aboutMe() { return this.newProfileForm.get('aboutMe') }
}
