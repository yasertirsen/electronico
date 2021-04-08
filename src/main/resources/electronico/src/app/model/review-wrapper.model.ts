import {Review} from "./review.model";

export interface ReviewWrapper{
  reviews: Review[];
  rating: number;
}
