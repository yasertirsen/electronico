import {Component, OnInit} from '@angular/core';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {Observable} from 'rxjs';
import {map, shareReplay} from 'rxjs/operators';
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {MatSidenav} from "@angular/material/sidenav";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserService} from "../service/user.service";
import {User} from "../model/user.model";

@Component({
  selector: 'app-main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: ['./main-nav.component.css']
})
export class MainNavComponent implements OnInit{
  activeRoute: string | undefined;
  user: User;
  hidden = true;
  unauthenticatedRoutes = ['/login', '/register', '/'];

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(private breakpointObserver: BreakpointObserver, private activatedRouter: ActivatedRoute,
              private router: Router, private userService: UserService, private _snackBar: MatSnackBar) {
    this.router.events.subscribe((event) => {
      if(event instanceof NavigationEnd && event.url) {
        this.activeRoute = event.url;
      }
    });
  }

  ngOnInit(): void {
    if(!!localStorage.getItem('currentUser')) {
      this.user = JSON.parse(<string>localStorage.getItem('currentUser'));
      this.hidden = this.user.cart.items.length === 0;
      }
    }


  onLogout(drawer: MatSidenav) {
    drawer.toggle()
    this.userService.logout();
    this._snackBar.open('Logout successful', 'Close', {
      duration: 3000
    });
  }

  isAuthenticated() {
    return !!localStorage.getItem('currentUser');
  }

  isAdmin() {
      return !!localStorage.getItem('currentUser') &&
        JSON.parse(<string>localStorage.getItem('currentUser')).role === 'ROLE_ADMIN';
  }
}
