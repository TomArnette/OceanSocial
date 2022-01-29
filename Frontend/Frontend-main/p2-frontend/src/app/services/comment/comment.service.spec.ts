import { HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core/testing';
import { of } from 'rxjs';
import { Comment } from 'src/app/models/Comment';
import { Post } from 'src/app/models/Post';
import { User } from 'src/app/models/User';
import { asyncData, asyncError } from 'src/testing/async-observable-helpers';
import { UtilityService } from '../utility.service';

import { CommentService } from './comment.service';

describe('CommentService', () => {
  let httpClientSpy: { get: jasmine.Spy };
  let commentService: CommentService;
  let utilityService: UtilityService;

  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
    commentService = new CommentService(httpClientSpy as any, utilityService);
  })

  xit('should return expected comments', (done: DoneFn) => {
    const expectedComments: Comment[] = [/* {
      "commentId": 1,
      "commText": "comment text",
      "post": new Post(),
      "user": new User()
    } */];

    httpClientSpy.get.and.returnValue(asyncData(expectedComments));

    commentService.getCommentsByPostId(1)
      .subscribe(comments => {
        expect(comments).toEqual(expectedComments);
        done();
      },
      done.fail
    );
    expect(httpClientSpy.get.calls.count()).toBe(1);
  });

  xit('should return an error when the server returns a 404', (done: DoneFn) => {
    const errorResponse = new HttpErrorResponse({
      error: 'test 404 error',
      status: 404, statusText: 'Not Found'
    });

    httpClientSpy.get.and.returnValue(asyncError(errorResponse));

    commentService.getCommentsByPostId(5)
      .subscribe(
        comments => done.fail('expected an error, not comments'),
        error => {
          expect(error.message).toContain('test 404 error');
          done();
        }
      );
  });
});
