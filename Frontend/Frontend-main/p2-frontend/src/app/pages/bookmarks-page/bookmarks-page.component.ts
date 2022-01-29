import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-bookmarks-page',
  templateUrl: './bookmarks-page.component.html',
  styleUrls: ['./bookmarks-page.component.css']
})
export class BookmarksPageComponent implements OnInit {

  pageCount = 1;
  userObj = {};
  title : string = "Bookmarked Posts"
  _userIsPosting: boolean = false;
  _notificationsDisplay: boolean = false;

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.userObj = JSON.parse(sessionStorage.getItem('userObj')!);
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
