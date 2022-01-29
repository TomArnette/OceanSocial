import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UserService } from 'src/app/services/user/user.service';

import { CommentComponent } from './comment.component';

describe('CommentComponent', () => {
  let component: CommentComponent;
  let fixture: ComponentFixture<CommentComponent>;
  let userService: jasmine.SpyObj<UserService>;

  beforeEach(async () => {
    const userServiceSpy = jasmine.createSpyObj('UserService', ['getUserById']);
    await TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule ],
      declarations: [ CommentComponent ],
      providers: [ {provide: UserService, useValue: userServiceSpy} ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    userService = TestBed.inject(UserService) as jasmine.SpyObj<UserService>
    fixture = TestBed.createComponent(CommentComponent);
    component = fixture.componentInstance;
   
    fixture.detectChanges();
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });

  });

  /* it('should create', () => {
    expect(component).toBeTruthy();
  }); */
});
