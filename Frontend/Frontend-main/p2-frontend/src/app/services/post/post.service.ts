import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { shareReplay } from 'rxjs/operators';
import { Post } from 'src/app/models/Post';
import { UtilityService } from '../utility.service';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  jwtToken = sessionStorage.getItem('JWT');

  headers = new HttpHeaders().set('content-type', 'application/json')
                             .set('Access-Control-Allow-Origin', '*')
                             .set('authorization', this.jwtToken);

  constructor(private httpCli: HttpClient, private utilityService: UtilityService) { }

  createPost(post: Post) {
    this.setHeaders();
    return this.httpCli.post(`${this.utilityService.getServerDomain()}/api/feed/post`, post, {'headers': this.headers})
  }

  getAllPosts(page: number) {
    this.setHeaders();
    return this.httpCli.get<any>(`${this.utilityService.getServerDomain()}/api/feed/post/fave/${page}`,  {'headers': this.headers} )
  }

  getPostsByUserId(userId: number, page: number){
    this.setHeaders();
    return this.httpCli.get<any>(`${this.utilityService.getServerDomain()}/api/feed/post/userId/${userId}/${page}`, {'headers': this.headers});
  }

  getPostByPostId(postId: number){
    this.setHeaders();
    return this.httpCli.get<any>(`${this.utilityService.getServerDomain()}/api/feed/post/${postId}`, {'headers': this.headers})
  }

  getAllPostsForOneUser(userId: number, page: number) {
    this.setHeaders();
    return this.httpCli.get<any>(`${this.utilityService.getServerDomain()}/api/feed/post/userId/${userId}/${page}`, {'headers': this.headers})
  }

  getNextPageOfPosts(pageCount: number) {
    this.setHeaders();
    return this.httpCli.get<any>(`${this.utilityService.getServerDomain()}/api/feed/post/fave/${pageCount}`, {'headers': this.headers})
  }

  setHeaders(): void {
    this.jwtToken = sessionStorage.getItem('JWT');

    this.headers = new HttpHeaders().set('Content-type', 'application/json')
                             .set('authorization', this.jwtToken);
  }
}
