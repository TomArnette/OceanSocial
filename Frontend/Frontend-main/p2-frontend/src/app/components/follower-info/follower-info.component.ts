import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscriber } from 'rxjs';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-follower-info',
  templateUrl: './follower-info.component.html',
  styleUrls: ['./follower-info.component.css']
})

export class FollowerInfoComponent implements OnInit {

  userIdFromParam: number = this.route.snapshot.params["id"];
  followLabel : string = "Follow";
  followed: boolean = false;
  followers: number = 0;
  following: number = 0;
  allFollowing: Subscriber<any> = new Subscriber;
  userInSession:number;
  closeResult = '';

  arrayOfFollowers: any[] = [];


  constructor(private userService: UserService, private route: ActivatedRoute, private modalService: NgbModal, private router:Router) {}

  ngOnInit(): void {

    this.followed = false;

    this.userInSession = JSON.parse(sessionStorage.getItem('userObj')).userId;
    this.userService.getUserById(this.userIdFromParam).subscribe(follows =>{
       follows.data.followers.forEach(element => {
         this.userService.getUserById(element).subscribe(followerUser => {
           this.arrayOfFollowers.push(followerUser);
         })
        if(element == this.userInSession){
          this.followLabel = "Unfollow";
          this.followed = true;
        }
      });
      this.following = follows.data.user_following.length;
      this.followers = follows.data.followers.length;

    })
  }

  follow(){
    this.userIdFromParam = this.route.snapshot.params["id"];

    if(!this.followed)
    {
     this.userService.followUser(this.userIdFromParam, this.userInSession).subscribe(responseData =>{

       if(responseData.success){
        this.followers = +this.followers +  1;
        this.followLabel = "Unfollow"
        this.followed = !this.followed;
       }
    })
  }
    else{
      this.userService.unfollowUser(this.userIdFromParam, this.userInSession).subscribe(response=>{
        if(response.success){
        this.followers = +this.followers - 1;
        this.followLabel = "Follow"
        this.followed = !this.followed;
      }}
      )}
  }

  open(content: any) {

    this.modalService.open(content,
   {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult =
         `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  openFollowingPage(){
    this.router.navigateByUrl(`/following/${this.userIdFromParam}`)
  }

  goToProfile(followerId: number){
    this.router.navigateByUrl(`/profile-feed/${followerId}`).then(() => window.location.reload());
  }

}
