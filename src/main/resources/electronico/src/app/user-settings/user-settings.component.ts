import {Component, OnInit} from '@angular/core';
import {User} from "../model/user.model";
import {MatDialog} from "@angular/material/dialog";
import {SettingsDialogComponent} from "./settings-dialog/settings-dialog.component";
import {UserService} from "../service/user.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.css']
})
export class UserSettingsComponent implements OnInit {
  user: User;

  constructor(private dialog: MatDialog, private userService: UserService, private _snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.user = JSON.parse(<string>localStorage.getItem('currentUser'));
  }

  onEdit() {
    const settingsDialog =
      this.dialog.open(SettingsDialogComponent, {
        width: '500px',
        data: this.user
      });
    settingsDialog.afterClosed().subscribe(result => {
      if(result !== undefined) {
        this.userService.updateUser(result).subscribe(data => {
          console.log(data);
          localStorage.setItem('currentUser', JSON.stringify(data));
          this._snackBar.open('Saved',
            'Close', {duration: 3000});
        });
      }
    });
  }
}
