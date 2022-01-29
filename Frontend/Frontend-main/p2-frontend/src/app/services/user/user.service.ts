import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/User';
import { UtilityService } from '../utility.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  searchOption=[]
  public userData: User[] | undefined
  jwtToken = sessionStorage.getItem('JWT');

  headers = new HttpHeaders().set('Content-type', 'application/json')
                             .set('authorization', this.jwtToken);

  constructor(private httpCli: HttpClient, private utilityService: UtilityService, private router: Router) {}

  getAllUsers():Observable<any> {
    return this.httpCli.get<any>(`${this.utilityService.getServerDomain()}/api/user/user`);
  }

  getUserById(id: number): Observable<any> {
    this.setHeaders();
    return this.httpCli.get<any>(`${this.utilityService.getServerDomain()}/api/user/user/${id}`, {'headers': this.headers})
  }

  register(user: User): Observable<any> {
    return this.httpCli.post(`${this.utilityService.getServerDomain()}/api/user/user`, user);
  }

  // This can be deleted
  createProfile(user: User): Observable<any> {
    this.setHeaders();
    return this.httpCli.post(`${this.utilityService.getServerDomain()}/api/user/createProfile`, user, {'headers': this.headers});
  }

  updateProfile(user: User): Observable<any> {
    this.setHeaders();
    return this.httpCli.put(`${this.utilityService.getServerDomain()}/api/user/updateUser`, user, {'headers': this.headers});
  }

  login(user: User): Observable<any> {
    return this.httpCli.post(`${this.utilityService.getServerDomain()}/api/user/login`, user);
  }

  // we are now handling logout from the frontend, so no need to use httpClient
  logout() {
    sessionStorage.clear();
    this.router.navigateByUrl('');
  }

  addProfileImage(formData: FormData): Observable<any> {
    return this.httpCli.post(`${this.utilityService.getServerDomain()}/api/user/profile`, formData);
  }

  addPostImage(formData: FormData): Observable<any> {
    return this.httpCli.post(`${this.utilityService.getServerDomain()}/api/feed/image`, formData);
  }

  forgotPassword(username: string): Observable<any> {
    return this.httpCli.get(`${this.utilityService.getServerDomain()}/api/user/forgot/${username}`);
  }

  getUserNotifications(userId: number): Observable<any> {
    this.setHeaders();
    return this.httpCli.get(`${this.utilityService.getServerDomain()}/api/user/notification/${userId}`, { 'headers': this.headers });
  }

  getAllFollowing(loggedInUser: number): Observable<any>{
    this.setHeaders();
    return this.httpCli.get(`${this.utilityService.getServerDomain()}/api/user/follow/${loggedInUser}`, {'headers': this.headers});
  }

   getAllFollowers(): Observable<any>{
    this.setHeaders();
    return this.httpCli.get(`${this.utilityService.getServerDomain()}/api/user/follower`, {'headers': this.headers})
  }

  followUser(userId: number, loggedInUser: number): Observable<any>{
    this.setHeaders();
    return this.httpCli.post(`${this.utilityService.getServerDomain()}/api/user/follow/${loggedInUser}`, `${userId}`, {'headers': this.headers});
  }

   unfollowUser(userId: number, loggedInUser: number): Observable<any>{
    this.setHeaders();
    return this.httpCli.delete(`${this.utilityService.getServerDomain()}/api/user/follow/${loggedInUser}`, {'headers': this.headers,'body': `${userId}`});
  }

  setHeaders(): void {
    this.jwtToken = sessionStorage.getItem('JWT');

    this.headers = new HttpHeaders().set('Content-type', 'application/json')
                             .set('authorization', this.jwtToken);
  }
}
