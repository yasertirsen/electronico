import {Injectable} from '@angular/core';
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

  register(user: RegisterRequest) {
    return this.http.post('http://localhost:8084/api/user/register', user);

  }

  login(details: LoginRequest): Observable<any> {
    return this.http.post<any>('http://localhost:8084/api/user/login',
      {"email": details.email,
            "password": details.password})
      .pipe(map(user => {
              if(user && user.token) {
                localStorage.setItem('token', user.token);
                localStorage.setItem('email', user.email);
                localStorage.setItem('expiresIn', user.expiresIn);
                localStorage.setItem('currentUser', JSON.stringify(user));
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

  updateUser(user: User): Observable<any>{
    return this.http.put('http://localhost:8084/api/user/update', user);
  }
}
