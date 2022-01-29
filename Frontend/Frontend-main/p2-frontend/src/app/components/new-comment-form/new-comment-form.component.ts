import { Component, Input, OnInit} from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { CommentService } from 'src/app/services/comment/comment.service';

@Component({
  selector: 'app-new-comment-form',
  templateUrl: './new-comment-form.component.html',
  styleUrls: ['./new-comment-form.component.css']
})
export class NewCommentFormComponent implements OnInit {

  submitLabel: string = "Submit";

  user:number = 0 ;

  @Input()
  parent:number|undefined = 0;

  newCommentForm = this.fb.group({
    postText: ['', [Validators.required, Validators.maxLength(150)]],
    postParentId:  [null],
    userId: [null]
  })

  constructor(private fb: FormBuilder, private commentService: CommentService, private router: Router) { }

  ngOnInit() {
    this.user =  JSON.parse(sessionStorage.getItem('userObj')!).userId
  }

 onClick(event: any) {
    if (this.newCommentForm.invalid) {
      return;
    }


   this.newCommentForm.patchValue({
    postParentId: this.parent,
    userId: this.user
  })

    this.commentService.createComment(this.newCommentForm.value)
      .subscribe(
        data => {

          this.router.navigateByUrl(this.router.url)

        }
      )
  }

  get postText() { return this.newCommentForm.get('postText') }
}
