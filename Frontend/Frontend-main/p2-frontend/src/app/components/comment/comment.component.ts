import { Component, Input, OnInit, Output } from '@angular/core';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs';
import { Comment } from 'src/app/models/Comment';
import { CommentService } from 'src/app/services/comment/comment.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {

  @Input()
  comment: any;
  @Output()
  firstName: string =  "";

  username: string = "";
  proPicUrl: string = "";
  userObj :any = {}
  postId:number = 0;
  userId:number = 0;
  toggleCommentsText: string = "view";
  showComments: boolean;
  display: boolean;
  commentCount: number;
  closeResult: string;
  userObs: Subscription;

  constructor(private commentService: CommentService, private userService: UserService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.userObs = this.userService.getUserById(this.comment.userId).subscribe(
      data =>{
        this.firstName = data.data.firstName;
        this.proPicUrl = data.data.proPicUrl;
        this.username = data.data.username;
      }
    )
  }

  ngOnDestroy(): void {
    this.userObs.unsubscribe();
  }

  toggleComments() {
    if (this.toggleCommentsText == 'view') {
      this.toggleCommentsText = 'hide';

    } else {
      this.toggleCommentsText = 'view';
    }

    this.showComments = !this.showComments;
  }

  exit(){
    this.display = false;
  }

  displayModal(){
    this.display = true;
  }

  receiveCommentCount(count: number) {
    this.commentCount = count;
  }

  open(content: any) {
    this.modalService.open(content,
   {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult =
         `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }
}
