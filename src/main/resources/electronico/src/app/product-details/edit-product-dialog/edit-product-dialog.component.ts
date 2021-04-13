import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Product} from "../../model/product.model";

@Component({
  selector: 'app-edit-product-dialog',
  templateUrl: './edit-product-dialog.component.html',
  styleUrls: ['./edit-product-dialog.component.css']
})
export class EditProductDialogComponent implements OnInit {

  selectedCategory: string;
  categories = ['Gaming Consoles', 'Laptop and PC', 'Smartphones', 'Accessories', 'Cameras', 'Home', 'Other']

  constructor(public editProduct: MatDialogRef<EditProductDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {product: Product}) { }

  onNoClick(): void {
    this.editProduct.close();
  }

  ngOnInit(): void {
  }

}
