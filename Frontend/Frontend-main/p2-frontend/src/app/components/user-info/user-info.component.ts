import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { User } from 'src/app/models/User';
import { PostService } from 'src/app/services/post/post.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent implements OnInit {

  username: string | undefined;
  firstName: string | undefined;
  lastName: string | undefined;
  aboutMe: string | undefined;
  bday: string | undefined;
  proPicUrl: string | undefined;
  // variables to be set from session storage
  userObj: any = {};
  navigationSubscription: any;


  constructor(private route: ActivatedRoute, private postServ: PostService, private userService: UserService, private router: Router) {
    this.navigationSubscription = this.router.events.subscribe((e: any) => {
      if (e instanceof NavigationEnd) this.ngOnInit();
    })
  }

  ngOnInit(): void {
    let userId: number = this.route.snapshot.params["id"];

    this.userService.getUserById(userId).subscribe(user1 => {
      this.userObj = user1.data;
      this.username = this.userObj.username;
      this.firstName = this.userObj.firstName;
      this.lastName = this.userObj.lastName;
      this.bday = this.userObj.bday;
      this.proPicUrl = this.userObj.proPicUrl;
      this.aboutMe = this.userObj.aboutMe;
    })

  }

}

