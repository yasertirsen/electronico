import {Component, OnInit} from '@angular/core';
import {ProductService} from "../service/product.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Product} from "../model/product.model";
import {MatDialog} from "@angular/material/dialog";
import {ReviewDialogComponent} from "./review-dialog/review-dialog.component";
import {Review} from "../model/review.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {User} from "../model/user.model";
import {ReviewService} from "../service/review.service";
import {CartService} from "../service/cart.service";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {
  product: Product;
  reviews: Review[];
  rating: number;
  quantity: number = 1;
  loading = true;
  isAdmin = false;
  user: User;

  constructor(private productService: ProductService, private activatedRoute: ActivatedRoute,
              private router: Router, private dialog: MatDialog, private _snackBar: MatSnackBar,
              private reviewService: ReviewService, private cartService: CartService) { }

  ngOnInit(): void {
    this.user = JSON.parse(<string>localStorage.getItem('currentUser'));
    this.isAdmin = this.user.role === 'ROLE_ADMIN';
    this.productService.get(this.activatedRoute.snapshot.params.productId).subscribe(data => {
      this.product = data;
      this.reviewService.getReviews(this.product.productId).subscribe(data => {
        this.reviews = data.reviews;
        this.rating = data.rating;
        this.loading = false;
      });
    },
      error => {
        this.router.navigateByUrl('/notFound')
      });
  }

  getImg(image: string | SVGImageElement) {
    return 'data:image/jpeg;base64,' + image
  }

  onReview() {
      const reviewDialog =
        this.dialog.open(ReviewDialogComponent, {
          width: '500px',
          data: {productId: this.product.productId, userId: this.user.userId}
        });
    reviewDialog.afterClosed().subscribe(result => {
        if(result !== undefined) {
          this.reviewService.review(result).subscribe(data => {
            this._snackBar.open('Review posted successfully',
              'Close', {duration: 3000});
            window.location.reload();
          });
        }
      });
  }

  onAddToCart() {
    this.user.cart.items.push({itemId: 0, product: this.product, quantity: this.quantity});
    this.cartService.update(this.user.cart).subscribe(data => {
      this.user.cart = data;
      localStorage.setItem('currentUser', JSON.stringify(this.user));
      this._snackBar.open('Product added to cart',
        'Close', {duration: 3000});
    });
  }
}
