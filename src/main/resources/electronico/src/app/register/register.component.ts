import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {RegisterRequest} from "../model/register-request.model";
import {UserService} from "../service/user.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  hide = true;
  model: RegisterRequest = {
    username:'',
    email:'',
    password:''
  };

  constructor(private userService: UserService, private router: Router,
              private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  register(): void {
    this.userService.register(this.model).subscribe(data => {
      this.router.navigate(['/login'],
        {queryParams: {registered: 'true'}});
    }, error => {
      console.log(error);
      this._snackBar.open('Registration Failed! Please try again', 'Close',
        {
          duration: 5000
        });
    });
  }
}
