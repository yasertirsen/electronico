import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Router} from "@angular/router";
import {LoginRequest} from "../model/login-request-payload";
import {User} from "../model/user.model";
import {RegisterRequest} from "../model/register-request.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  tokenExpirationTimer: any;

  constructor(private http: HttpClient, private router: Router) {}

  public getToken(): string {
    return <string>localStorage.getItem('token');
  }

  login(details: LoginRequest): Observable<any> {
    return this.http.post<any>('http://localhost:8084/api/user/login',
      {"email": details.email,
            "password": details.password})
      .pipe(map(user => {
              if(user && user.token) {
                localStorage.setItem('currentUser', JSON.stringify(user));
                localStorage.setItem('token', user.token);
                localStorage.setItem('email', user.email);
                localStorage.setItem('expiresIn', user.expiresIn);
              }
              this.autoLogout(user.expiresIn);
              return user;
    }));
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.router.navigateByUrl('/login');
    if(this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
  }

  autoLogout(expirationDuration: number) {
    this.tokenExpirationTimer = setTimeout(() => {
      this.logout();
    }, expirationDuration);
  }

  getUserById(userId: number): Observable<any> {
    return this.http.get('http://localhost:8083/getUserById/' + userId);
  }

  getUserAvatar(userId: number): Observable<any>{
    return this.http.get('http://localhost:8083/getStudentAvatar/' + userId);
  }

  verifyToken(token: string, password: string): Observable<any>{
    return this.http.put('http://localhost:8083/changePassword/' + token, {},
      {params: {password: password}});
  }

  sendVerify(email: string): Observable<any>{
    return this.http.get('http://localhost:8083/sendVerify/',
      {params: {email: email}});
  }


  updateUser(user: User): Observable<any>{
    return this.http.put('http://localhost:8083/update',
      {
        "userId": user.userId,
        "fullName": user.fullName,
        "password": user.password,
        "email": user.email,
        "username": user.username,
        "address": user.address,
        "enabled": user.enabled,
        "role": user.role,
        "authorities": user.authorities,
        "isLocked": user.isLocked
      });
  }

  // getSkillsNames(profile: Profile): Observable<any>{
  //   return this.http.post('http://localhost:8083/getSkillsNames',
  //     {
  //       "profileId": profile.profileId,
  //       "course": profile.course,
  //       "bio": profile.bio,
  //       "externalSkills": profile.externalSkills,
  //       "projects": profile.projects,
  //       "experiences": profile.experiences,
  //       "averageGrade": profile.averageGrade,
  //       "startCourse": profile.startCourse,
  //       "endCourse": profile.endCourse,
  //       "age": profile.age,
  //       "race": profile.race,
  //       "gender": profile.gender
  //     });
  // }
  //
  // updateProfile(profile: ProfileModel): Observable<any>{
  //   return this.http.put('http://localhost:8083/updateProfile',
  //     {
  //       "profileId": profile.profileId,
  //       "course": profile.course,
  //       "bio": profile.bio,
  //       "externalSkills": profile.externalSkills,
  //       "projects": profile.projects,
  //       "experiences": profile.experiences,
  //       "averageGrade": profile.averageGrade,
  //       "startCourse": profile.startCourse,
  //       "endCourse": profile.endCourse,
  //       "age": profile.age,
  //       "race": profile.race,
  //       "gender": profile.gender
  //     });
  // }

  uploadImage(imageData: FormData, userId: number): Observable<any> {
    return this.http.post('http://localhost:8083/upload/image/' + userId,
      imageData);
  }

  register(user: RegisterRequest) {
    return this.http.post('http://localhost:8084/api/user/register', {
      "username": user.username,
      "password": user.password,
      "email": user.email
    });

  }
}
