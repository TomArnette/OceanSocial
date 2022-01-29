import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { BookmarkService } from 'src/app/services/bookmark/bookmark.service';
import { PostService } from 'src/app/services/post/post.service';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {

  @Input() pageCount: number = 1;
  previousPage: number;
  postList: Array<any> = [];
  postObs: Subscription = new Subscription();
  bookmarkObs: Subscription = new Subscription();
  hasLoadedPosts: boolean = false;
  stringInput: string = "";
  navigationSubscription: any;
  userObj: any;
  updateFeed: boolean;
  hasReachedLastPage: boolean = false;

  constructor(private postServ: PostService, private bookmarkServ: BookmarkService, private router: Router, private route: ActivatedRoute) {

    this.router.routeReuseStrategy.shouldReuseRoute = () => false;

    this.navigationSubscription = this.router.events.subscribe((e: any) => {
      if (e instanceof NavigationEnd) this.ngOnInit();
    })
  }

  ngOnInit(): void {
    this.userObj = JSON.parse(sessionStorage.getItem('userObj'));
  }

  ngAfterViewInit() {
    this.populateFeed();
    this.hasLoadedPosts = true;
  }

  ngOnChanges(changes: SimpleChanges) {

    if (this.hasLoadedPosts && !this.hasReachedLastPage) {
      this.populateFeed();
    }

  }

  ngOnDestroy(): void{
    this.postObs.unsubscribe();
    this.bookmarkObs.unsubscribe();

    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }

  populateFeed(){

    if (this.route.snapshot.params["id"]) {
      this.getOneUsersPosts();
    } else if (this.router.url == '/bookmarks') {
      this.getBookmarkedPosts();
    } else {
      this.getAllFollowedPosts();
    }

  }

  getBookmarkedPosts() {
    this.bookmarkObs = this.bookmarkServ.getBookmarks(this.userObj.userId, this.pageCount).subscribe((response)=>{

      if (!response.success) {
        this.hasReachedLastPage = true;
      } else {
        response.data.forEach(bookmarkId => {
          this.postObs = this.postServ.getPostByPostId(bookmarkId).subscribe((postResponse)=>{
            this.postList.push(postResponse.data);
          })
        });
      }
    })
  }

  getAllFollowedPosts() {
    this.postObs = this.postServ.getNextPageOfPosts(this.pageCount).subscribe(posts => {
      if (!posts.success) {
        this.hasReachedLastPage = true;
      } else {
        posts.data.forEach(post => {
          this.postList.push(post);
        });
      }
    })
  }

  getOneUsersPosts() {
    this.postObs = this.postServ.getAllPostsForOneUser(this.route.snapshot.params["id"], this.pageCount)
    .subscribe(posts => {

      if (!posts.success) {
        this.hasReachedLastPage = true;
      } else {
        posts.data.forEach(post => {
          this.postList.push(post);
        });
      }
    })
  }
}

