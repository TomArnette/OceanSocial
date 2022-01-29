import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from 'src/app/models/Comment';
import { UtilityService } from '../utility.service';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  jwtToken = sessionStorage.getItem('JWT');

  headers = new HttpHeaders().set('content-type', 'application/json')
                             .set('Access-Control-Allow-Origin', '*')
                             .set('authorization', this.jwtToken);

  constructor(private httpCli: HttpClient, private utilityService: UtilityService) { }

  createComment(comment: Comment) {
    this.setHeaders();
    return this.httpCli.post(`${this.utilityService.getServerDomain()}/api/feed/post`, comment, {'headers': this.headers});
  }

  getCommentsByPostId(postId:number): Observable<any>{
    this.setHeaders();
    return this.httpCli.get(`${this.utilityService.getServerDomain()}/api/feed/post/comment/${postId}`, {'headers': this.headers})
  }

  setHeaders(): void {
    this.jwtToken = sessionStorage.getItem('JWT');

    this.headers = new HttpHeaders().set('Content-type', 'application/json')
                             .set('authorization', this.jwtToken);
  }
}

