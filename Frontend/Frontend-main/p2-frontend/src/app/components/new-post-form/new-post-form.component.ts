import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { PostService } from 'src/app/services/post/post.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-new-post-form',
  templateUrl: './new-post-form.component.html',
  styleUrls: ['./new-post-form.component.css']
})
export class NewPostFormComponent implements OnInit{

  displayUrl: any;
  submitLabel: string = "Submit";
  imageUrl: string = "";
  userId: number = 0;
  @Input() userObj = {};

  imageForm = this.fb.group({
    imageFile: [null]
  })

  newPostForm = this.fb.group({
    postPicUrl: [''],
    postText: ['', [Validators.required, Validators.maxLength(250)]],
    postYouUrl: [''],
    userId: [this.userId]
  })


  constructor(private fb: FormBuilder, private userService: UserService, private postService: PostService, private router: Router) { }
  ngOnInit(): void {
    this.userId =  JSON.parse(sessionStorage.getItem('userObj')!).userId
  }

  onFileInput(event: any) {
    if (event.currentTarget.files.length > 0) {
      const file = event.currentTarget.files[0];

      let reader = new FileReader();
      reader.readAsDataURL(file);

      reader.onload = (_event) => {
        this.displayUrl = reader.result;
      }

      this.imageForm.get('imageFile')?.setValue(file, {emitModelToViewChange: false});

    }
  }

  createPost() {
    if (this.newPostForm.invalid) {
      return;
    }

    this.newPostForm.patchValue({
      postPicUrl: this.imageUrl,
      userId: this.userId
    })

    this.postService.createPost(this.newPostForm.value)
      .pipe(first())
      .subscribe(
        data => {
          this.router.navigateByUrl(this.router.url);
          this.postText.reset();
        }
      )
  }

  uploadImageAndCreatePost(event: any) {
    if (this.imageForm.get('imageFile').value != null) {
      const formData = new FormData();
      formData.append('file', this.imageForm.get('imageFile').value);

      this.userService.addPostImage(formData)
        .subscribe(
          data => {
            this.imageUrl = data.data;

            this.createPost();

          }
        )
    } else {
      this.createPost();
    }
  }

  get postText() { return this.newPostForm.get('postText') }
  get postYouUrl() { return this.newPostForm.get('postYouUrl') }
}
