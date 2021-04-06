import {Product} from "./product.model";

export interface Item {
  itemId: number;
  product: Product;
  quantity: number;
}
