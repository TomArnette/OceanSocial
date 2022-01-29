import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Like } from 'src/app/models/Like';
import { UtilityService } from '../utility.service';

@Injectable({
  providedIn: 'root'
})
export class LikeService {

  jwtToken = sessionStorage.getItem('JWT');

  headers = new HttpHeaders().set('content-type', 'application/json')
                             .set('Access-Control-Allow-Origin', '*')
                             .set('authorization', this.jwtToken);

  constructor(private httpCli: HttpClient, private utilityService: UtilityService) { }

  likePost(like: any){
    this.setHeaders();
    return this.httpCli.post(`${this.utilityService.getServerDomain()}/api/feed/like`, like, {'headers': this.headers})
  }

  unLikePost(likeId: number){
    this.setHeaders();
    return this.httpCli.delete(`${this.utilityService.getServerDomain()}/api/feed/like/${likeId}`, {'headers': this.headers})
  }

  checkLike(postId : number, userId : number){
    this.setHeaders();
    return this.httpCli.get<any>(`${this.utilityService.getServerDomain()}/api/feed/like/${postId}/${userId}`, {'headers': this.headers})
  }

  getAllLikesByPost(postId: number){
    this.setHeaders();
    return this.httpCli.get<any>(`${this.utilityService.getServerDomain()}/api/feed/like/${postId}`, {'headers': this.headers})
  }

  setHeaders(): void {
    this.jwtToken = sessionStorage.getItem('JWT');

    this.headers = new HttpHeaders().set('Content-type', 'application/json')
                             .set('authorization', this.jwtToken);
  }
}
