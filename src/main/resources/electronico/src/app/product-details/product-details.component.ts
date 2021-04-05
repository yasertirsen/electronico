import { Component, OnInit } from '@angular/core';
import {ProductService} from "../service/product.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Product} from "../model/product.model";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {
  product: Product;
  loading = true;

  constructor(private productService: ProductService, private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.productService.get(this.activatedRoute.snapshot.params.productId).subscribe(data => {
      this.product = data;
      this.loading = false;
    },
      error => {
        this.router.navigateByUrl('/notFound')
      });
  }

  getImg(image: string | SVGImageElement) {
    return 'data:image/jpeg;base64,' + image
  }

}
