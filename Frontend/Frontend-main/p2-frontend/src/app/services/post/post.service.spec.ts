import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { result } from 'lodash';
import { of } from 'rxjs';
import { Post } from 'src/app/models/Post';
import { UtilityService } from '../utility.service';

import { PostService } from './post.service';

describe('PostService', () => {
  let service: PostService;
  let fixture: ComponentFixture<PostService>;
  let httpMock: HttpTestingController;
  let utilityService: UtilityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PostService, UtilityService]
    });
    service = TestBed.inject(PostService);
    httpMock = TestBed.inject(HttpTestingController);
    utilityService = TestBed.inject(UtilityService);
  });

  /* it('should be created', () => {
    expect(service).toBeTruthy();
  }); */

  it('should return post when create Post called', () => {
    service.createPost(new Post).subscribe((result: Post) => {
      expect(result).toEqual(new Post)
    })

    const req = httpMock.expectOne(`${utilityService.getServerDomain()}/api/feed/post`, 'create new post');
    expect(req.request.method).toBe('POST');

    req.flush(new Post);

    httpMock.verify();
  })

  it('should return allPosts when getAllPosts called', (pageNumber: number = 1) => {
    service.getAllPosts(pageNumber).subscribe((result: any) => {
      expect(result).toEqual(jasmine.arrayContaining(typeof Post));
    })

    const req = httpMock.expectOne(`${utilityService.getServerDomain()}/api/feed/post/fave/${pageNumber}`, 'get all posts first page');
    expect(req.request.method).toBe('GET');

    req.flush(jasmine.arrayContaining(typeof Post));

    httpMock.verify();
  })

  it('should return posts by userId when getPostByUserId called', (userId: number = 1, pageNumber: number = 1) => {
    service.getPostsByUserId(userId, pageNumber).subscribe((result: any) => {
      expect(result).toEqual(jasmine.arrayContaining(typeof Post));
    })

    const req = httpMock.expectOne(`${utilityService.getServerDomain()}/api/feed/post/userId/${userId}/${pageNumber}`, 'get posts by user id');

    expect(req.request.method).toBe('GET');

    req.flush(jasmine.arrayContaining(typeof Post));

    httpMock.verify();
  })

  it('should return posts by page count', (pageCount: number = 2) => {
    service.getNextPageOfPosts(pageCount).subscribe((result: any) => {
      expect(result).toEqual(jasmine.arrayContaining(typeof Post))
    })

    const req = httpMock.expectOne(`${utilityService.getServerDomain()}/api/feed/post/fave/${pageCount}`, 'get posts by page number');
    expect(req.request.method).toBe('GET')

    req.flush(jasmine.arrayContaining(typeof Post))

    httpMock.verify();
  })
});
