import {Component, OnInit, ViewChild} from '@angular/core';
import {ProductService} from "../service/product.service";
import {Product} from "../model/product.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  products: Product[];
  loading = true;

  constructor(private productService: ProductService) {
    this.productService.getAll().subscribe(data => {
      this.products = data;
      this.loading = false;
    },
      error => {
      console.log(error);
      });
  }

  ngOnInit(): void {
  }
}
