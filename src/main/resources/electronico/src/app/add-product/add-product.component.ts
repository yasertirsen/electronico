import { Component, OnInit } from '@angular/core';
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
  product: Product = {
    productId: 0,
    title: '',
    manufacturer: '',
    price: 0,
    category: '',
    description: '',
    image: null,
    stock: 0
  }
  selectedFile: File;
  selectedCategory: string;
  categories = ['Gaming Consoles', 'Laptop and PC', 'Smartphones', 'Accessories', 'Cameras', 'Other']

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
