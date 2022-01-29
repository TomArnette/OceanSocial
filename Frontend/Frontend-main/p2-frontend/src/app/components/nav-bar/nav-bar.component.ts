import { Component, DoCheck, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { empty, Subscription } from 'rxjs';
import { first } from 'rxjs/operators';
import { User } from 'src/app/models/User';
import { UserService } from 'src/app/services/user/user.service';


@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  userId!: number;
  _isInNav : boolean = true;
  logOutLabel: string = 'Logout';
  profilePosition: string = "-64rem";
  observer: Subscription = new Subscription();
  userList: Array<any> = [];
  listTemp: Array<User> = [];

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.userId = JSON.parse(sessionStorage.getItem('userObj')!).userId;

  }

  logout(event: any) {
    this.userService.logout();
  }

  goHome(){
    this.router.navigate([`/userFeed`])
  }

  toggleProfile(event: any) {
    if (this.profilePosition == "-64rem") {
      this.profilePosition = "0";
    } else {
      this.profilePosition = "-64rem";
    }
  }

  gotoBookmarks(){
    this.router.navigateByUrl(`/bookmarks`)
  }

  onViewById() {
    this.router.navigateByUrl(`/userFeed/${this.userId}`)
  }

  explore(){
    this.router.navigateByUrl(`/explore`)
  }

}
