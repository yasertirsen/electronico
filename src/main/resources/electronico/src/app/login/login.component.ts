import {Component, OnInit} from '@angular/core';
import {LoginRequest} from "../model/login-request-payload";
import {first} from "rxjs/operators";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserService} from "../service/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  hide = true;
  returnUrl: string;
  model: LoginRequest = {
    email:'',
    password: ''
  };

  constructor(private userService: UserService,
              private activatedRoute: ActivatedRoute, private router: Router,
              private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams
      .subscribe(params => {
        if (params.registered !== undefined && params.registered === 'true') {
          this._snackBar.open('Please check your email inbox to '
            + 'activate your account before you login!', 'Close', {
            duration: 5000
          });
        }
        this.returnUrl = params.returnUrl? params.returnUrl: '/home'
      });
  }

  login(): void {
    this.userService.login(this.model)
      .pipe(first())
      .subscribe(data => {
      this.router.navigateByUrl(this.returnUrl);
    }, error => {
      this._snackBar.open('Please check your credentials and try again.', 'Close', {
        duration: 5000,
      });
    });
  }
}


