import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { Notification } from '../model/notification';
import { Apiresponse } from '../apiresponse';

@Injectable({
  providedIn: 'root'
})
export class NotificationServiceService {

  constructor(private httpClient: HttpClient) { }
  private baseURL = 'http://localhost:8084/notification/create';

  createNotification(notification: Notification): Observable<Notification> {
    console.log('Creating notification:', notification);
    let headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const authHeaders = this.createAuthorizationHeader();
    if (authHeaders.has('Authorization')) {
      headers = headers.set('Authorization', authHeaders.get('Authorization')!);
    }
    return this.httpClient.post<any>(this.baseURL, notification, { headers }).pipe(
      map(this.mapToNotification),
      catchError(error => {
        console.error('Error creating notification:', error);
        return throwError(() => new Error(error));
      })
    );
  }

  updateNotification(notification: Notification): Observable<Notification> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.post<Apiresponse<Notification>>(`http://localhost:8084/notification/update`, notification, { headers }).pipe(
      map(response => {
        if (response.success) {
          return this.mapToNotification(response.data);
        }
        else {
          throw new Error(response.message);
        }
      })
    );
  }

  getNotificationById(id: number): Observable<Notification[]> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Notification[]>>(`http://localhost:8084/notification/findByUser?userId=${id}`, { headers }).pipe(
      map(response => {
        if (response.success) {
          return response.data.map(this.mapToNotification);
        }
        else {
          throw new Error(response.message);
        }
      })
    );
  }

  private createAuthorizationHeader(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    if (token) {
      console.log('Token found in local store:', token);
      return new HttpHeaders().set('Authorization', `Bearer ${token}`);
    }
    else {
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
