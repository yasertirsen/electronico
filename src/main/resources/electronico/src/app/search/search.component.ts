import { Component, OnInit } from '@angular/core';
import {Product} from "../model/product.model";
import {ActivatedRoute} from "@angular/router";
import {ProductService} from "../service/product.service";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  products: Product[];
  loading = true;
  searchUrl: any;

  constructor(private activatedRoute: ActivatedRoute, private productsService: ProductService) {
    let type =  this.activatedRoute.snapshot.params.type;
    let keywords =  this.activatedRoute.snapshot.params.keywords;

    if(type === 'name')
      this.searchUrl = this.productsService.searchByTitle(keywords);
    if(type === 'category')
      this.searchUrl = this.productsService.searchByCategory(keywords);
    if(type === 'manufacturer')
      this.searchUrl = this.productsService.searchByManufacturer(keywords);

    this.searchUrl.subscribe((data: Product[]) => {
      this.products = data;
      this.loading = false;
    });
  }

  ngOnInit(): void {
  }

}
