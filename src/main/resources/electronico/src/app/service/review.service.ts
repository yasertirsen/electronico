import {Injectable} from '@angular/core';
import {Review} from "../model/review.model";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private http: HttpClient) { }

  review(review: Review): Observable<any> {
    return this.http.post('http://localhost:8084/api/review/add', review);
  }

  getReviews(productId: number): Observable<any> {
    return this.http.get('http://localhost:8084/api/review/all/' + productId);
  }
}
