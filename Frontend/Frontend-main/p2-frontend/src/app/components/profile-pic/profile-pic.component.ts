import { Component, Input, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-profile-pic',
  templateUrl: './profile-pic.component.html',
  styleUrls: ['./profile-pic.component.css']
})
export class ProfilePicComponent implements OnInit {

  profilePic: string = '';

  @Input()
  passed:string;

  @Input()
  imgSrc: string;

  constructor() { }

  ngOnInit(): void {
    
    this.profilePic = JSON.parse(sessionStorage.getItem('userObj')).proPicUrl;    

  }

}
