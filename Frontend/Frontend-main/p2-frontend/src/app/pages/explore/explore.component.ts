import { Component, HostListener, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscriber } from 'rxjs';
import { User } from 'src/app/models/User';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['./explore.component.css']
})
export class ExploreComponent implements OnInit {
  userview: number = 0;
  pageCount = 1;
  aUser= {
    user: {userId : this.userview }
  }

  title: string = "Find a Friend!";
  
  @Input()
  user: User = {
    userId: undefined,
    username: '',
    password: undefined,
    email: undefined,
    firstName: undefined,
    lastName: undefined,
    aboutMe: undefined,
    bday: undefined,
    proPicUrl: undefined
  }

  userObj: any = {};
  userId: number;
  userList: Array<any> = []; 
  wholeList: Array<any> = []; 
  listNum: number = 5;
  
  following: any[];
  observer: Subscriber<any> = new Subscriber;

  _userIsPosting: boolean = false;
  _notificationsDisplay: boolean = false;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.userObj = JSON.parse(sessionStorage.getItem('userObj'));
    this.userId = JSON.parse(sessionStorage.getItem('userObj')).userId;

    if (this.userId == null) {
      this.router.navigateByUrl('')
    }

    this.userService.getAllUsers().subscribe(users => {
      this.wholeList = users.data;
      for(let item in this.wholeList) {
        this.wholeList[item].followLabel = "Follow";
      }
      
      this.userList = this.wholeList.slice(0, this.listNum)
      for (let user of this.wholeList) {
        
        this.userService.getUserById(this.userId).subscribe(responseData => {
          this.following = responseData.data.user_following;
          for (let followee of this.following) {
            if(user.userId == followee) {
              user.followLabel = "Unfollow";
            }
          }
        })}
    }) 
    
 
  }

  
  follow(userFollow: any){
        
    if(userFollow.followLabel == "Follow"){
      
      this.userService.followUser(userFollow.userId, this.userId).subscribe(responseData =>{
      
      if(responseData.success){
         userFollow.followLabel = "Unfollow"
         
       }else {
         
       }
    })
  }else {
    this.userService.unfollowUser(userFollow.userId, this.userId).subscribe(response=>{
        if(response.success){
          userFollow.followLabel = "Follow"
          
        }}
    )}
  }

  @HostListener("window:scroll", [])
  onScroll(): void {
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
      
      this.listNum = this.listNum + 5;
      this.userList = this.wholeList.slice(0, this.listNum);
      
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


