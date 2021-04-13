import {Component, OnInit} from '@angular/core';
import {ProductService} from "../service/product.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Product} from "../model/product.model";
import {MatDialog} from "@angular/material/dialog";
import {ReviewDialogComponent} from "./review-dialog/review-dialog.component";
import {Review} from "../model/review.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {User} from "../model/user.model";
import {CartService} from "../service/cart.service";
import {UserService} from "../service/user.service";
import {EditProductDialogComponent} from "./edit-product-dialog/edit-product-dialog.component";

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
  user: User;

  constructor(private productService: ProductService, private activatedRoute: ActivatedRoute,
              private router: Router, private dialog: MatDialog, private _snackBar: MatSnackBar,
              private cartService: CartService, private userService: UserService) { }

  ngOnInit(): void {
    this.user = JSON.parse(<string>localStorage.getItem('currentUser'));
    this.productService.get(this.activatedRoute.snapshot.params.productId).subscribe(data => {
      this.product = data;
      this.productService.getRating(this.product.productId).subscribe(data => {
        this.rating = data;
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

  isAdmin() {
    return this.userService.isAdmin();
  }

  onReview() {
      const reviewDialog =
        this.dialog.open(ReviewDialogComponent, {
          width: '500px',
          data: {productId: this.product.productId, userId: this.user.userId}
        });
    reviewDialog.afterClosed().subscribe(result => {
        if(!!result) {
          this.product.reviews.push(result)
          this.productService.update(this.product).subscribe(data => {
            this.product = data;
            this._snackBar.open('Review posted successfully',
              'Close', {duration: 3000});
          });
        }
      });
  }

  onAddToCart() {
    this.user.cart.items.push({itemId: 0, product: this.product, quantity: this.quantity});
    this.cartService.update(this.user.cart).subscribe(data => {
      this.user.cart = data;
      localStorage.setItem('currentUser', JSON.stringify(this.user));
    });
  }

  onEdit() {
    const editDialog =
      this.dialog.open(EditProductDialogComponent, {
        width: '500px',
        data: {product: this.product}
      });
    editDialog.afterClosed().subscribe(data => {
      if(!!data) {
        this.productService.update(data).subscribe(data => {
          this.product = data;
          this._snackBar.open('Product updated successfully',
            'Close', {duration: 3000});
        });
      }
    });
  }
}
