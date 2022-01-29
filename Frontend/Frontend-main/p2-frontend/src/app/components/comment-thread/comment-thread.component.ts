import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CommentService } from 'src/app/services/comment/comment.service';

@Component({
  selector: 'app-comment-thread',
  templateUrl: './comment-thread.component.html',
  styleUrls: ['./comment-thread.component.css']
})
export class CommentThreadComponent implements OnInit {

  commentThread: Array<any> = [];
  commentObs: Subscription = new Subscription();

  @Output() commentCount: EventEmitter<number> = new EventEmitter<number>();
  @Input()
  parentId: number = 0;

  constructor(private router: Router, private commentService: CommentService) { }

  ngOnInit(): void {
    this.commentObs = this.commentService.getCommentsByPostId(this.parentId).subscribe(comments => {
      if(comments.data != null){
      this.commentThread = comments.data;
      this.commentCount.emit(this.commentThread.length);
      }
    })
  }

  ngOnDestroy() {
    this.commentObs.unsubscribe();
  }
}
