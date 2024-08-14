import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core'
import { Notification } from '../../model/notification';
import { NotificationServiceService } from '../../service/notification-service.service';
import { User } from '../../model/user';


@Component({
  selector: 'app-notification',
  standalone: true,
  imports: [CommonModule,],
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.css'
})
export class NotificationComponent implements OnInit {
  
  userCurrent: User = new User();
  notifications : Notification[] = [];
  newNotification : Notification = new Notification();

  text: Notification [] = [
    {
      id: 1,
      message: 'This is a new notification',
      read: false
    },
    {
      id: 2,
      message: 'This is a new notification',
      read: false
    },
    {
      id: 3,
      message: 'This is a new notification',
      read: false
    },
    {
      id: 4,
      message: 'This is a new notification',
      read: false
    },
    {
      id: 5,
      message: 'This is a new notification',
      read: false
    },
    {
      id: 6,
      message: 'This is a new notification',
      read: false
    },
    {
      id: 7,
      message: 'This is a new notification',
      read: false
    },
    {
      id: 8,
      message: 'This is a new notification',
      read: false
    },
    {
      id: 9,
      message: 'This is a new notification',
      read: false
    },
    {
      id: 10,
      message: 'This is a new notification',
      read: false
    }
  ];

  constructor(private notificationService: NotificationServiceService) { }

  ngOnInit(): void {
    const userCurrentString = localStorage.getItem('userCurrent');
    if (userCurrentString) {
      this.userCurrent = JSON.parse(userCurrentString);
    }
    if(this.userCurrent.id){
      this.getNotificationById(this.userCurrent.id);
    }
    
  }

  markAsRead(notification: Notification) {
    notification.read = true;
    this.notificationService.updateNotification(notification).subscribe(data => {
      console.log(data);
    });
  }

  allNotificationsRead(): boolean {
    return this.notifications.every((notification) => notification.read);
  }

  getNotificationById(id: number) {
    this.notificationService.getNotificationById(id).subscribe(data => {
      this.notifications = data;
      console.log(data);
    })
  }

}
