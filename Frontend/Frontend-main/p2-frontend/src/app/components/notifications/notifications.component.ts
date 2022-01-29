import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit, OnDestroy {
  notifications: Array<any> = [];
  userId: number = 0;
  @Input() 
  userObj: any;
  @Input()
  lastNotification: number = 0;
  observer: Subscription = new Subscription();

  constructor(private userService: UserService, private router: Router) {
    this.userObj = JSON.parse(sessionStorage.getItem('userObj')!);
    if (this.userObj == null) this.router.navigateByUrl('');
    this.userId = this.userObj.userId;
 }
    
  ngOnInit(): void {
    this.getAllNotifications(this.userId);
  }

  ngOnDestroy(): void {
    this.observer.unsubscribe();
  }

  getAllNotifications(userId: number) {
    this.observer.unsubscribe();
    this.observer = this.userService.getUserNotifications(userId).subscribe(notification => {
      this.notifications = notification.data;
    })
  }

}
