import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Product} from "../model/product.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  add(product: Product): Observable<any> {
    return this.http.post('http://localhost:8084/api/product/add', product);
  }

  get(productId: number): Observable<any> {
    return this.http.get('http://localhost:8084/api/product/get/' + productId);
  }

  update(product: Product): Observable<any> {
    return this.http.put('http://localhost:8084/api/product/update', product);
  }

  uploadImage(productId: number, imageData: FormData): Observable<any> {
    return this.http.post('http://localhost:8084/api/product/upload-image/' + productId, imageData);
  }

  getAll(): Observable<any> {
    return this.http.get('http://localhost:8084/api/product/get/all');
  }

  searchByTitle(title: string): Observable<any> {
    return this.http.get('http://localhost:8084/api/product/searchByTitle/' + title);
  }

  searchByCategory(category: string): Observable<any> {
    return this.http.get('http://localhost:8084/api/product/searchByCategory/' + category);
  }

  searchByManufacturer(manufacturer: string): Observable<any> {
    return this.http.get('http://localhost:8084/api/product/searchByManufacturer/' + manufacturer);
  }

  getRating(productId: number): Observable<any> {
    return this.http.get('http://localhost:8084/api/product/rating/' + productId);
  }
}
