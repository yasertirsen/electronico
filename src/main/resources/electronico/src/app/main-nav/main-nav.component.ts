import {Component, OnInit} from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay, filter } from 'rxjs/operators';
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {MatSidenav} from "@angular/material/sidenav";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserService} from "../service/user.service";

@Component({
  selector: 'app-main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: ['./main-nav.component.css']
})
export class MainNavComponent {
  activeRoute: string | undefined;

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


  onLogout(drawer: MatSidenav) {
    drawer.toggle()
    this.userService.logout();
    this._snackBar.open('Logout successful', 'Close', {
      duration: 3000
    });
  }
}