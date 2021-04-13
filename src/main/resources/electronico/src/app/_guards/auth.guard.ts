import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from "rxjs";
import {User} from "../model/user.model";

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  constructor(private router: Router) { }

  private static _verifyRoles(route: ActivatedRouteSnapshot, user: User): boolean {
    let found = false;

    for (let i = 0; i < route.data.authorities.length; i++) {
      found = !!user.authorities.find(auth => auth === route.data.authorities[i]);
      if (found)
        return found;
    }
    return found;
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    | boolean
    | UrlTree
    | Promise<boolean | UrlTree>
    | Observable<boolean | UrlTree> {
    let user = JSON.parse(<string>localStorage.getItem('currentUser'));
    if (!!user) {
      // logged in so return true
      if (route.data.authorities && !AuthGuard._verifyRoles(route, user)) {
        this.router.navigate(['/notFound']);
      }
      return true;
    }

    // not logged in so redirect to login page with the return url
    this.router.navigate(['/login'], { queryParams: { returnUrl: state.url }});
    return false;
  }
}
