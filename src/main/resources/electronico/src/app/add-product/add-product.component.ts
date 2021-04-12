import {Component, OnInit} from '@angular/core';
import {Product} from "../model/product.model";
import {ProductService} from "../service/product.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  product: Product = new Product();
  selectedFile: File;
  selectedCategory: string;
  categories = ['Gaming Consoles', 'Laptop and PC', 'Smartphones', 'Accessories', 'Cameras', 'Home', 'Other']

  constructor(private productService: ProductService, private _snackBar: MatSnackBar,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  onFileChanged(event: Event) {
    // @ts-ignore
    this.selectedFile = event.target.files[0];
  }

  onAdd(): void {
    const imageData = new FormData();
    imageData.append('imageFile', this.selectedFile);
    imageData.get('imageFile')
    this.product.category = this.selectedCategory;
    console.log(this.product);
    this.productService.add(this.product).subscribe(data => {
      this.product = data;
      this.productService.uploadImage(this.product.productId, imageData).subscribe(res => {
        this._snackBar.open('Added successfully', 'Close', {duration: 3000});
        this.router.navigateByUrl('/home')
      },
        error => {
          console.log(error);
          this._snackBar.open('Error Uploading', 'Close', {duration: 3000});
        });

    },
      error => {
      console.log(error);
      this._snackBar.open('Error', 'Close', {duration: 3000});
      });
  }
}
