import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {User} from "../../model/user.model";
import {Payment} from "../../model/payment.model";

@Component({
  selector: 'app-choose-payment-method-dialog',
  templateUrl: './choose-payment-method-dialog.component.html',
  styleUrls: ['./choose-payment-method-dialog.component.css']
})
export class ChoosePaymentMethodDialogComponent implements OnInit {
  selectedPayment: Payment;

  constructor(public choosePayment: MatDialogRef<ChoosePaymentMethodDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public user: User) { }

  onNoClick(): void {
    this.choosePayment.close();
  }
  ngOnInit(): void {
  }

}
