import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {UserService} from "../../service/user.service";
import {Observable} from "rxjs";

@Injectable({providedIn: 'root'})
export class TokenInterceptor implements HttpInterceptor {
  constructor(public userService: UserService) {}
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if(!localStorage.getItem('currentUser')) {
      return next.handle(request);
    }
    else {
      const tokenizedRequest = request.clone({
        setHeaders: {
          Authorization: `Bearer ${this.userService.getToken()}`
        }
      });
      return next.handle(tokenizedRequest);
    }
  }
}
