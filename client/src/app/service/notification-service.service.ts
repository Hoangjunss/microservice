import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { Notification } from '../model/notification';

@Injectable({
  providedIn: 'root'
})
export class NotificationServiceService {

  constructor(private httpClient:HttpClient) { }
  private baseURL = 'http://localhost:8084/notification/create';

  createNotification(notification: Notification):Observable<Notification> {
    console.log('Creating notification:', notification);
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post<any>(this.baseURL, notification, { headers }).pipe(
      map(this.mapToNotification),
      catchError(error => {
        console.error('Error creating notification:', error);
        return throwError(() => new Error(error));
      })
    );
  }

  updateNotification(notification: Notification):Observable<Notification> {
    return this.httpClient.post<any>(`http://localhost:8084/notification/update`, notification).pipe(
      map(this.mapToNotification),
      catchError(error => {
        console.error('Error updating notification:', error);
        return throwError(() => new Error(error));
      })
    );
  }

  getNotificationById(id: number):Observable<Notification[]> {
    return this.httpClient.get<any[]>(`http://localhost:8084/notification/findByUser?userId=${id}`).pipe(
      map(notificationDTO => notificationDTO.map(this.mapToNotification)),
      catchError(error => {
        console.error('Error getting notification:', error);
        return throwError(() => new Error(error));
      })
    );
  }

  private createAuthorizationHeader(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    if(token){
      console.log('Token found in local store:', token);
      return new HttpHeaders().set('Authorization', `Bearer ${token}`);
    }
    else
    {
      console.log('Token not found in local store');
    }
    return new HttpHeaders();
  }

  private mapToNotification(notificationDTO: any): Notification {
    return {
      id: notificationDTO.id,
      message: notificationDTO.message,
      createAt: notificationDTO.createAt,
      url: notificationDTO.url,
      idUser: notificationDTO.idUser,
      read: notificationDTO.read
    };
  }
}
