import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { User } from 'src/app/models/User';

import { SearchComponent } from './search.component';

describe('SearchComponent', () => {
  let component: SearchComponent;
  let fixture: ComponentFixture<SearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      declarations: [ SearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchComponent);
    window.sessionStorage.setItem('userObj', JSON.stringify(new User()))
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display all users in listTemp', ()=>{

    let user0 = new User();
    user0.username = "Adam";
    let user1 = new User();
    user1.username = "Dave";
    let user2 = new User();
    user2.username = "Abby";
    let user3 = new User();
    user3.username = "Amanda";

    component.userList.push(user0);
    component.userList.push(user1);
    component.userList.push(user2);
    component.userList.push(user3);

    component.searchInput = "A"
    component.ngDoCheck();

    fixture.detectChanges();

    expect(document.getElementsByTagName("app-user-link").length).toBe(3);

    component.searchInput = "D"
    component.ngDoCheck();

    fixture.detectChanges();

    expect(document.getElementsByTagName("app-user-link").length).toBe(1);
  })

});
