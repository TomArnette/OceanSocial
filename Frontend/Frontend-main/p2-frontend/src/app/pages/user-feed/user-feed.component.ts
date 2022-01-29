import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/app/models/User';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-user-feed',
  templateUrl: './user-feed.component.html',
  styleUrls: ['./user-feed.component.css']
})
export class UserFeedComponent implements OnInit {

  pageCount = 1;
  userObj = {};
  title : string = "Your Current"
  _userIsPosting: boolean = false;
  _notificationsDisplay: boolean = false;

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.userObj = JSON.parse(sessionStorage.getItem('userObj'));

    if(this.userObj == null){
      this.router.navigateByUrl('')
    }
  }

  @HostListener("window:scroll", [])
  onScroll(): void {
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
      this.pageCount++;
    }
  }

  receivePostStatus(postCheck: boolean) {
    this._notificationsDisplay = false;
    this._userIsPosting = postCheck;
  }

  receiveNotificationStatus(notificationCheck: boolean) {
    this._userIsPosting = false;
    this._notificationsDisplay = notificationCheck;
  }

}
