import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Cart} from "../model/cart.model";
import {Order} from "../model/order.model";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http: HttpClient) { }

  update(cart: Cart): Observable<any> {
    return this.http.put('http://localhost:8084/api/cart/update', cart);
  }

  pay(order: Order, userId: number): Observable<any> {
    return this.http.post('http://localhost:8084/api/cart/pay/' + userId, order);
  }
}
