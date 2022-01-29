import { Component, DoCheck, Input, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit{

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

  userId: number;
  searchInput:string= "";
  userList: Array<any> = [];
  listTemp: Array<User> = [];
  isTyping: boolean = false;

  constructor(private userService: UserService) { }


  ngOnInit(): void {
    this.userId = JSON.parse(sessionStorage.getItem('userObj')).userId;

    this.userService.getAllUsers().subscribe(users => {
      this.userList = users.data;
    })
  }

  ngDoCheck(): void {
    if(this.searchInput != "" ){
      this.isTyping = true;
      this.listTemp = this.userList.filter(user => user.username.startsWith(this.searchInput))

    }else{
      this.listTemp = []
      this.isTyping = false;
    }

  }

}
