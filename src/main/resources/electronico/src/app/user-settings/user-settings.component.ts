import {Component, OnInit} from '@angular/core';
import {User} from "../model/user.model";
import {MatDialog} from "@angular/material/dialog";
import {SettingsDialogComponent} from "./settings-dialog/settings-dialog.component";
import {UserService} from "../service/user.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {AddPaymentMethodDialogComponent} from "./add-payment-method-dialog/add-payment-method-dialog.component";

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
      if(!!result) {
        this.updateUser(result, 'Saved');
      }
    });
  }

  onAdd() {
    const addPaymentMethodDialog =
      this.dialog.open(AddPaymentMethodDialogComponent, {
        width: '500px'
      });
    addPaymentMethodDialog.afterClosed().subscribe(result => {
      if(!!result) {
        console.log(result);
        if(!result.cardNumber || !result.cardName || !result.expiry) {
          this._snackBar.open('Please make sure all card information is entered',
            'Close', {duration: 3000});
        }
        else {
          this.user.paymentMethods.push(result);
          this.updateUser(this.user, 'Payment method added successfully');
        }
      }
    });
  }

  updateUser(user: User, message: string) {
    this.userService.updateUser(user).subscribe(data => {
      console.log(data);
      localStorage.setItem('currentUser', JSON.stringify(data));
      this._snackBar.open(message,
        'Close', {duration: 3000});
    });
  }
}
