import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-add-payment-method-dialog',
  templateUrl: './add-payment-method-dialog.component.html',
  styleUrls: ['./add-payment-method-dialog.component.css']
})
export class AddPaymentMethodDialogComponent implements OnInit {
  cardForm: FormGroup;

  constructor(public addPayment: MatDialogRef<AddPaymentMethodDialogComponent>,
              private fb: FormBuilder) { }

  onNoClick(): void {
    this.addPayment.close();
  }

  onSubmit(){
    this.addPayment.close(this.cardForm.value);
  }

  ngOnInit(): void {
    this.cardForm = this.fb.group({
      cardNumber: ['', Validators.required],
      cardName: ['', Validators.required],
      expiry: ['', Validators.required]
    });
  }

}
