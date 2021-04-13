import {Component, OnInit} from '@angular/core';
import {User} from "../model/user.model";
import {CartService} from "../service/cart.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialog} from "@angular/material/dialog";
import {ChoosePaymentMethodDialogComponent} from "./choose-payment-method-dialog/choose-payment-method-dialog.component";
import {Order} from "../model/order.model";

@Component({
  selector: 'app-view-cart',
  templateUrl: './view-cart.component.html',
  styleUrls: ['./view-cart.component.css']
})
export class ViewCartComponent implements OnInit {
  user: User;
  order: Order = new Order();

  constructor(private cartService: CartService, private _snackBar: MatSnackBar,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.user = JSON.parse(<string>localStorage.getItem('currentUser'));
  }

  onDelete(index: number) {
    if(index > -1) {
      this.user.cart.items.splice(index, 1);
      this.cartService.update(this.user.cart).subscribe(data =>{
        this.user.cart = data;
        this.updateUser();
        this._snackBar.open('Item deleted',
          'Close', {duration: 3000});
      });
    }
  }

  getImg(image: string | SVGImageElement) {
    return 'data:image/jpeg;base64,' + image
  }

  onPay() {
    if(this.user.paymentMethods.length === 0) {
      this._snackBar.open('Please add a payment method in settings to complete order',
        'Close', {duration: 3000});
    }
    else {
      const choosePayment =
        this.dialog.open(ChoosePaymentMethodDialogComponent, {
          width: '500px',
          data: this.user
        });
      choosePayment.afterClosed().subscribe(result => {
        if(!!result) {
          this.order.paymentMethod = result;
          this.order.items = this.user.cart.items;
          this.order.total = this.user.cart.total;
          this.cartService.pay(this.order, this.user.userId).subscribe(data => {
              this.user = data;
              this.updateUser();
              console.log(data);
              this._snackBar.open('Payment successful',
                'Close', {duration: 3000});
            },
            error => {
              this._snackBar.open('Payment failed',
                'Close', {duration: 3000});
            });
        }
      });
    }
  }

  private updateUser() {
    localStorage.setItem('currentUser', JSON.stringify(this.user));
  }
}
