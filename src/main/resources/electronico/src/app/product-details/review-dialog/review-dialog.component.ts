import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Review} from "../../model/review.model";

@Component({
  selector: 'app-review-dialog',
  templateUrl: './review-dialog.component.html',
  styleUrls: ['./review-dialog.component.css']
})
export class ReviewDialogComponent implements OnInit {
  review: Review;

  constructor(public reviewDialog: MatDialogRef<ReviewDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {productId: number, userId: number}) { }

  onNoClick(): void {
    this.reviewDialog.close();
  }

  ngOnInit(): void {
    this.review = {
      reviewId: 0,
      reviewContent: '',
      rating: 1,
      productId: this.data.productId,
      userId: this.data.userId
    };
  }

}
