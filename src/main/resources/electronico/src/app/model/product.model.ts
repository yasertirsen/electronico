import {Review} from "./review.model";

export class Product {
  productId: number;
  title: string;
  manufacturer: string;
  price: number;
  category: string;
  description: string;
  image: any;
  stock: number;
  reviews: Review[];
}
