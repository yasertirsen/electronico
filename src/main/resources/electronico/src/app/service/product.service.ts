import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Product} from "../model/product.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  add(product: Product): Observable<any> {
    return this.http.post('http://localhost:8084/api/product/add', {
      "title": product.title,
      "manufacturer": product.manufacturer,
      "price": product.price,
      "category": product.category,
      "image": product.image,
      "stock": product.stock,
    });
  }

  update(product: Product): Observable<any> {
    return this.http.put('http://localhost:8084/api/product/update', {
      "title": product.title,
      "manufacturer": product.manufacturer,
      "price": product.price,
      "category": product.category,
      "image": product.image,
      "stock": product.stock,
    });
  }

  uploadImage(productId: number, imageData: FormData): Observable<any> {
    return this.http.post('http://localhost:8084/api/product/upload-image/' + productId, imageData);
  }
}
