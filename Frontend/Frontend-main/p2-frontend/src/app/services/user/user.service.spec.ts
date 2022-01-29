import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { result } from 'lodash';
import { User } from 'src/app/models/User';
import { UtilityService } from '../utility.service';

import { UserService } from './user.service';

describe('UserService', () => {
  let service: UserService;
  let utilityService: UtilityService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [UserService, UtilityService]
    });

    service = TestBed.inject(UserService);
    utilityService = TestBed.inject(UtilityService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should return all users when getAllUsers called', () => {
    service.getAllUsers().subscribe((result:any) => {
      expect(result).toEqual(jasmine.arrayContaining(typeof User));
    })

    const req = httpMock.expectOne(`${utilityService.getServerDomain()}/api/user/user`, 'get all users');
    expect(req.request.method).toBe('GET');

    req.flush(jasmine.arrayContaining(typeof User));

    httpMock.verify();
  });

  it('should return user when getUserById is called', (userId: number = 1) => {

    service.getUserById(userId).subscribe((result: User) => {
      expect(result).toEqual(new User());
    })

    const req = httpMock.expectOne(`${utilityService.getServerDomain()}/api/user/user/${userId}`, 'get user by id 1');
    expect(req.request.method).toBe('GET');

    req.flush(new User);

    httpMock.verify();
  })

  it('should return user when account created', (() => {
    service.register(new User()).subscribe((result: User) => {
      expect(result).toEqual(new User());
    })

    const req = httpMock.expectOne(`${utilityService.getServerDomain()}/api/user/user`, 'regiter new user');
    expect(req.request.method).toBe('POST');

    req.flush(new User());

    httpMock.verify();

  }))

  it('should return user when user updated', (() => {
    service.updateProfile(new User()).subscribe((result: User) => {
      expect(result).toEqual(new User());
    })

    const req = httpMock.expectOne(`${utilityService.getServerDomain()}/api/user/updateUser`, 'update user profile');
    expect(req.request.method).toBe('PUT');

    req.flush(new User());

    httpMock.verify();
  }))

  it('should return user when login called', (() => {
    service.login(new User()).subscribe((result: User) => {
      expect(result).toEqual(new User());
    })

    const req = httpMock.expectOne(`${utilityService.getServerDomain()}/api/user/login`, 'user login');
    expect(req.request.method).toBe('POST');

    req.flush(new User());

    httpMock.verify();
  }))

  it('should return user email address when forgotPassword called', (() => {
    let username = 'username';
    service.forgotPassword(username).subscribe((result: string) => {
      expect(result).toEqual('user@example.com');
    })

    const req = httpMock.expectOne(`${utilityService.getServerDomain()}/api/user/forgot/${username}`);
    expect(req.request.method).toBe('GET');

    req.flush('user@example.com');

    httpMock.verify();
  }))
});
