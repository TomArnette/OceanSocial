import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  current: string = 'login';

  forgotForm = this.fb.group({
    username: [null, Validators.required]
  });

  constructor(private fb: FormBuilder, private router: Router, private userService: UserService) { }

  ngOnInit(): void {
    if (sessionStorage.getItem('userObj') != null) {
      this.router.navigateByUrl('userFeed');
    }
  }

  toggle(data: string) {
    this.current = data;
  }

  backToLogin() {
    this.current = 'login';
  }

}
