import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-view-profile',
  templateUrl: './view-profile.component.html',
  styleUrls: ['./view-profile.component.css']
})
export class ViewProfileComponent implements OnInit {

  updateLabel: string = "Update Profile"
  viewOrUpdate: string = 'view';
  firstName: string = '';
  lastName: string = '';
  username: string = '';
  bday: string = '';
  aboutMe: string = '';
  proPicUrl = '';
  navigationSubscription: any;

  // variables to be set from session storage
  userObj: any = {};

  constructor(private router: Router) {
    this.navigationSubscription = this.router.events.subscribe((e: any) => {
      if (e instanceof NavigationEnd) this.ngOnInit();
    })
  }

  ngOnInit(): void {
    this.userObj = JSON.parse(sessionStorage.userObj);
    this.firstName = this.userObj.firstName;
    this.lastName = this.userObj.lastName;
    this.username = this.userObj.username;
    this.bday = this.userObj.bday;
    this.aboutMe = this.userObj.aboutMe;
    this.proPicUrl = this.userObj.proPicUrl;
  }

  updateProfile(){
    if (this.viewOrUpdate == 'view') {
      this.viewOrUpdate = 'update';
      this.updateLabel = 'View Profile';
    } else {
      this.viewOrUpdate = 'view';
      this.updateLabel = 'Update Profile';
    }
  }

  @Output() public hide: EventEmitter<void> = new EventEmitter();
  toggleProfile(event: any) {
    this.hide.emit();
  }

  receiveOutputText(text: string) {
    this.viewOrUpdate = text;
    this.updateProfile();
  }

}
