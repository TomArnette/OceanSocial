import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { Post } from 'src/app/models/Post';
import { PostService } from 'src/app/services/post/post.service';
import { UtilityService } from 'src/app/services/utility.service';

import { FeedComponent } from './feed.component';

describe('FeedComponent', () => {
  let component: FeedComponent;
  let fixture: ComponentFixture<FeedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      declarations: [ FeedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create a post component for each post in postList', ()=>{

    component.postList.push(new Post());
    component.postList.push(new Post());

    fixture.detectChanges();

    expect(component.postList.length).toBe(2);
    expect(document.getElementsByTagName("app-post").length).toBe(2);
    

  })


});
