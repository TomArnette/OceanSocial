import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { PostService } from 'src/app/services/post/post.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-profile-feed',
  templateUrl: './profile-feed.component.html',
  styleUrls: ['./profile-feed.component.css']
})
export class ProfileFeedComponent implements OnInit {

  observer: Subscription = new Subscription();
  pageCount = 1;

  userObj = {};
  _userIsPosting: boolean = false;
  _notificationsDisplay: boolean = false;

  constructor(private route: ActivatedRoute, private postService: PostService) { }

  ngOnInit(): void {

 
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
